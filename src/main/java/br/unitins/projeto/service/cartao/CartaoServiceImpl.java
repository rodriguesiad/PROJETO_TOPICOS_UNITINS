package br.unitins.projeto.service.cartao;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

import br.unitins.projeto.dto.cartao.CartaoDTO;
import br.unitins.projeto.dto.cartao.CartaoResponseDTO;
import br.unitins.projeto.model.Cartao;
import br.unitins.projeto.repository.CartaoRepository;

@ApplicationScoped
public class CartaoServiceImpl implements CartaoService {

    @Inject
    CartaoRepository repository;

    @Inject
    Validator validator;

    @Override
    public List<CartaoResponseDTO> getAll() {
        List<Cartao> list = repository.listAll();
        return list.stream().map(CartaoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public CartaoResponseDTO findById(Long id) {
        Cartao cartao = repository.findById(id);

        if (cartao == null)
            throw new NotFoundException("Cartão não encontrado.");

        return new CartaoResponseDTO(cartao);
    }

    @Override
    @Transactional
    public CartaoResponseDTO create(CartaoDTO cartaoDTO) throws ConstraintViolationException {
        validar(cartaoDTO);

        Cartao entity = new Cartao();
        entity.setNumeroCartao(cartaoDTO.numeroCartao());
        entity.setNomeTitular(cartaoDTO.nomeTitular());
        entity.setHash(cartaoDTO.cvc() + cartaoDTO.dataVencimento());

        repository.persist(entity);

        return new CartaoResponseDTO(entity);
    }

    @Override
    @Transactional
    public CartaoResponseDTO update(Long id, CartaoDTO cartaoDTO) throws ConstraintViolationException {
        validar(cartaoDTO);

        Cartao entity = repository.findById(id);
        entity.setNumeroCartao(cartaoDTO.numeroCartao());
        entity.setNomeTitular(cartaoDTO.nomeTitular());
        entity.setHash(cartaoDTO.cvc() + cartaoDTO.dataVencimento());

        return new CartaoResponseDTO(entity);
    }

    private void validar(CartaoDTO cartaoDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<CartaoDTO>> violations = validator.validate(cartaoDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<CartaoResponseDTO> findByNumeroCartao(String numeroCartao) {
        List<Cartao> list = repository.findByNumeroCartao(numeroCartao);
        return list.stream().map(CartaoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public Cartao toModel(@Valid CartaoDTO cartaoDTO) {
        Cartao entity = new Cartao();

        entity.setNomeTitular(cartaoDTO.nomeTitular());
        entity.setNumeroCartao(cartaoDTO.numeroCartao());
        entity.setHash(cartaoDTO.cvc() + cartaoDTO.dataVencimento());

        return entity;
    }

}