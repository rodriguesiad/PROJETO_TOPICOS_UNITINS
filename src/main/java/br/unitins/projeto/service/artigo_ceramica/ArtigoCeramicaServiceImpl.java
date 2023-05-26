package br.unitins.projeto.service.artigo_ceramica;

import br.unitins.projeto.dto.artigo_ceramica.ArtigoCeramicaDTO;
import br.unitins.projeto.dto.artigo_ceramica.ArtigoCeramicaResponseDTO;
import br.unitins.projeto.form.ImageForm;
import br.unitins.projeto.model.ArtigoCeramica;
import br.unitins.projeto.model.ProdutoImagem;
import br.unitins.projeto.model.TipoProduto;
import br.unitins.projeto.repository.ArtesaoRepository;
import br.unitins.projeto.repository.ArtigoCeramicaRepository;
import br.unitins.projeto.repository.TipoProdutoRepository;
import br.unitins.projeto.service.file.FileService;
import br.unitins.projeto.service.produto_imagem.ProdutoImagemService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class ArtigoCeramicaServiceImpl implements ArtigoCeramicaService {

    @Inject
    ArtigoCeramicaRepository repository;

    @Inject
    ArtesaoRepository artesaoRepository;

    @Inject
    TipoProdutoRepository tipoProdutoRepository;

    @Inject
    Validator validator;

    @Inject
    FileService fileService;

    @Inject
    ProdutoImagemService produtoImagemService;

    @Override
    public List<ArtigoCeramicaResponseDTO> getAll() {
        List<ArtigoCeramica> list = repository.listAll();
        return list.stream().map(ArtigoCeramicaResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public ArtigoCeramicaResponseDTO findById(Long id) {
        ArtigoCeramica artigoCeramica = repository.findById(id);

        if (artigoCeramica == null) throw new NotFoundException("Produto não encontrado.");

        return new ArtigoCeramicaResponseDTO(artigoCeramica);
    }

    @Override
    @Transactional
    public ArtigoCeramicaResponseDTO create(ArtigoCeramicaDTO artigoCeramicaDTO) throws ConstraintViolationException {
        validar(artigoCeramicaDTO);

        ArtigoCeramica entity = new ArtigoCeramica();
        entity.setDescricao(artigoCeramicaDTO.descricao());
        entity.setNome(artigoCeramicaDTO.nome());
        entity.setPreco(artigoCeramicaDTO.preco());
        entity.setEstoque(artigoCeramicaDTO.estoque());
        entity.setQuantidadePecas(artigoCeramicaDTO.quantidadePecas());
        entity.setArtesao(artesaoRepository.findById(artigoCeramicaDTO.idArtesao()));

        // Tipo Produto
        if (artigoCeramicaDTO.tipoProduto() != null) {
            List<TipoProduto> tipoProdutos = new ArrayList<>();

            for (Long item : artigoCeramicaDTO.tipoProduto()) {
                tipoProdutos.add(tipoProdutoRepository.findById(item));
            }

            entity.setTipoProduto(tipoProdutos);
        }

        repository.persist(entity);

        return new ArtigoCeramicaResponseDTO(entity);
    }

    @Override
    @Transactional
    public ArtigoCeramicaResponseDTO update(Long id, ArtigoCeramicaDTO artigoCeramicaDTO) throws ConstraintViolationException {
        validar(artigoCeramicaDTO);

        ArtigoCeramica entity = repository.findById(id);
        entity.setDescricao(artigoCeramicaDTO.descricao());
        entity.setNome(artigoCeramicaDTO.nome());
        entity.setPreco(artigoCeramicaDTO.preco());
        entity.setEstoque(artigoCeramicaDTO.estoque());
        entity.setQuantidadePecas(artigoCeramicaDTO.quantidadePecas());
        entity.setArtesao(artesaoRepository.findById(artigoCeramicaDTO.idArtesao()));

        // Tipo Produto
        if (artigoCeramicaDTO.tipoProduto() != null) {
            List<TipoProduto> tipoProdutos = new ArrayList<>();

            for (Long item : artigoCeramicaDTO.tipoProduto()) {
                tipoProdutos.add(tipoProdutoRepository.findById(item));
            }

            entity.setTipoProduto(tipoProdutos);
        }

        return new ArtigoCeramicaResponseDTO(entity);
    }

    private void validar(ArtigoCeramicaDTO artigoCeramicaDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<ArtigoCeramicaDTO>> violations = validator.validate(artigoCeramicaDTO);

        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<ArtigoCeramicaResponseDTO> findByNome(String nome) {
        List<ArtigoCeramica> list = repository.findByNome(nome);
        return list.stream().map(ArtigoCeramicaResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public ArtigoCeramicaResponseDTO insertImagens(ImageForm form, Long id) throws IOException {
        ArtigoCeramica produto = repository.findById(id);
        String nomeImagem = "";

        if (produto == null) throw new NotFoundException("Produto não encontrado.");

        List<ProdutoImagem> imagens = produto.getImagens();

        if (imagens.isEmpty()) {
            imagens = new ArrayList<>();
        }

        nomeImagem = fileService.salvarImagem(form.getImagem(), form.getNomeImagem(), "produto", Long.toString(id));

        ProdutoImagem produtoImagem = new ProdutoImagem(nomeImagem, produto);
        produtoImagemService.create(produtoImagem);

        imagens.add(produtoImagem);


        produto.setImagens(imagens);

        return new ArtigoCeramicaResponseDTO(produto);
    }

}
