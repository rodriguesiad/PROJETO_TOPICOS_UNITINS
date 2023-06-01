package br.unitins.projeto.service.cartao;

import java.util.List;

import jakarta.validation.Valid;

import br.unitins.projeto.dto.cartao.CartaoDTO;
import br.unitins.projeto.dto.cartao.CartaoResponseDTO;
import br.unitins.projeto.model.Cartao;

public interface CartaoService {

    List<CartaoResponseDTO> getAll();

    CartaoResponseDTO findById(Long id);

    CartaoResponseDTO create(CartaoDTO productDTO);

    CartaoResponseDTO create(Cartao product);

    CartaoResponseDTO update(Long id, CartaoDTO productDTO);

    void delete(Long id);

    List<CartaoResponseDTO> findByNumeroCartao(String numeroCartao);

    Long count();

    Cartao toModel(@Valid CartaoDTO cartaoDTO);

}