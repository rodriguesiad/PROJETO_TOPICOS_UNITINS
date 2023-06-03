package br.unitins.projeto.service.compra;

import br.unitins.projeto.dto.compra.CompraDTO;
import br.unitins.projeto.dto.compra.CompraResponseDTO;
import br.unitins.projeto.dto.compra.StatusCompraDTO;
import br.unitins.projeto.dto.historico_entrega.HistoricoEntregaDTO;
import br.unitins.projeto.dto.historico_entrega.HistoricoEntregaResponseDTO;
import br.unitins.projeto.model.Compra;
import br.unitins.projeto.model.HistoricoEntrega;
import br.unitins.projeto.model.ItemCompra;
import br.unitins.projeto.model.StatusCompra;
import br.unitins.projeto.model.Usuario;
import br.unitins.projeto.repository.CompraRepository;
import br.unitins.projeto.repository.HistoricoEntregaRepository;
import br.unitins.projeto.repository.UsuarioRepository;
import br.unitins.projeto.service.endereco_compra.EnderecoCompraService;
import br.unitins.projeto.service.item_compra.ItemCompraService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@ApplicationScoped
public class CompraServiceImpl implements CompraService {

    @Inject
    CompraRepository repository;

    @Inject
    ItemCompraService itemCompraService;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    EnderecoCompraService enderecoCompraService;

    @Inject
    HistoricoEntregaRepository historicoEntregaRepository;

    @Inject
    Validator validator;

    @Override
    public List<CompraResponseDTO> getAll() {
        List<Compra> list = repository.listAll();
        return list.stream().map(compra -> CompraResponseDTO.valueOf(compra)).collect(Collectors.toList());
    }

    @Override
    public CompraResponseDTO findById(Long id) {
        Compra compra = repository.findById(id);

        if (compra == null)
            throw new NotFoundException("Compra não encontrada.");

        return CompraResponseDTO.valueOf(compra);
    }

    @Override
    @Transactional
    public CompraResponseDTO create(CompraDTO dto, Long idUsuario) {
        validar(dto);

        Compra entity = new Compra();

        entity.setData(LocalDateTime.now());
        entity.setUsuario(this.getUsuario(idUsuario));
        entity.setStatusCompra(StatusCompra.PROCESSANDO);
        entity.setEnderecoCompra(enderecoCompraService.toModel(dto.enderecoCompra()));

        repository.persist(entity);

        List<ItemCompra> itens = new ArrayList<>();
        AtomicReference<Double> preco = new AtomicReference<>(0.0);

        dto.itensCompra().forEach(item -> {
                    ItemCompra itemModel = itemCompraService.toModel(item);
                    itemModel.setCompra(entity);
                    itens.add(itemModel);

                    preco.updateAndGet(v -> v + (itemModel.getPreco() * itemModel.getQuantidade()));
                }
        );

        entity.setItensCompra(itens);
        entity.setTotalCompra(preco.get());

        return CompraResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public CompraResponseDTO alterStatusCompra(Long idCompra, StatusCompraDTO dto) {

        Compra compra = this.getCompra(idCompra);
        try {
            compra.setStatusCompra(StatusCompra.valueOf(dto.statusCompra()));
        } catch (Exception e) {
            throw new NotFoundException("Status não encontrado.");
        }

        return CompraResponseDTO.valueOf(compra);
    }

    @Override
    public List<CompraResponseDTO> findByUsuario(Long idUsuario) {
        List<Compra> list = repository.findByUsuario(idUsuario);
        return list.stream().map(compra -> CompraResponseDTO.valueOf(compra)).collect(Collectors.toList());
    }

    @Override
    public List<HistoricoEntregaResponseDTO> getHistoricoEntrega(Long idCompra) {
        List<HistoricoEntrega> list = historicoEntregaRepository.findByCompra(idCompra);
        return list.stream().map(HistoricoEntregaResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public HistoricoEntregaResponseDTO insertHistoricoEntrega(Long idCompra, HistoricoEntregaDTO dto) {
        Compra compra = this.getCompra(idCompra);

        HistoricoEntrega historicoEntrega = new HistoricoEntrega();
        historicoEntrega.setTitulo(dto.titulo());
        historicoEntrega.setDescricao(dto.descricao());
        historicoEntrega.setData(LocalDateTime.now());
        historicoEntrega.setCompra(compra);

        historicoEntregaRepository.persist(historicoEntrega);

        if (compra.getHistoricoEntrega().isEmpty())
            compra.setHistoricoEntrega(new ArrayList<>());

        compra.getHistoricoEntrega().add(historicoEntrega);

        return new HistoricoEntregaResponseDTO(historicoEntrega);
    }

    private void validar(CompraDTO dto) throws ConstraintViolationException {
        Set<ConstraintViolation<CompraDTO>> violations = validator.validate(dto);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    private Usuario getUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id);

        if (usuario == null)
            throw new NotFoundException("Usuário não encontrado.");

        return usuario;
    }

    private Compra getCompra(Long id) {
        Compra compra = repository.findById(id);

        if (compra == null)
            throw new NotFoundException("Compra não encontrada.");

        return compra;
    }

}
