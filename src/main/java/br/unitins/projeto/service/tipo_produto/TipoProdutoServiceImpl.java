package br.unitins.projeto.service.tipo_produto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

import br.unitins.projeto.dto.tipo_produto.TipoProdutoDTO;
import br.unitins.projeto.dto.tipo_produto.TipoProdutoResponseDTO;
import br.unitins.projeto.model.TipoProduto;
import br.unitins.projeto.repository.TipoProdutoRepository;

@ApplicationScoped
public class TipoProdutoServiceImpl implements TipoProdutoService {

    @Inject
    TipoProdutoRepository repository;

    @Inject
    Validator validator;

    @Override
    public List<TipoProdutoResponseDTO> getAll() {
        List<TipoProduto> list = repository.listAll();
        return list.stream().map(TipoProdutoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public TipoProdutoResponseDTO findById(Long id) {
        TipoProduto tipoProduto = repository.findById(id);

        if (tipoProduto == null)
            throw new NotFoundException("Tipo de Produto não encontrado.");

        return new TipoProdutoResponseDTO(tipoProduto);
    }

    @Override
    @Transactional
    public TipoProdutoResponseDTO create(TipoProdutoDTO tipoProdutoDTO) throws ConstraintViolationException {
        validar(tipoProdutoDTO);

        TipoProduto entity = new TipoProduto();
        entity.setDescricao(tipoProdutoDTO.descricao());
        repository.persist(entity);

        return new TipoProdutoResponseDTO(entity);
    }

    @Override
    @Transactional
    public TipoProdutoResponseDTO update(Long id, TipoProdutoDTO tipoProdutoDTO) throws ConstraintViolationException {
        validar(tipoProdutoDTO);

        TipoProduto entity = repository.findById(id);
        entity.setDescricao(tipoProdutoDTO.descricao());

        return new TipoProdutoResponseDTO(entity);
    }

    private void validar(TipoProdutoDTO tipoProdutoDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<TipoProdutoDTO>> violations = validator.validate(tipoProdutoDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<TipoProdutoResponseDTO> findByDescricao(String descricao) {
        List<TipoProduto> list = repository.findByDescricao(descricao);
        return list.stream().map(TipoProdutoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long count() {
        return repository.count();
    }

}
