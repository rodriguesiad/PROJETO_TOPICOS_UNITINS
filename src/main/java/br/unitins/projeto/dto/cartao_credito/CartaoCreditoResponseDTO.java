package br.unitins.projeto.dto.cartao_credito;

import br.unitins.projeto.dto.cartao.CartaoResponseDTO;
import br.unitins.projeto.model.Cartao;
import br.unitins.projeto.model.CartaoCredito;

public record CartaoCreditoResponseDTO(

        Long id,
        Integer quantidadeParcelas,
        Double valorParcela,
        CartaoResponseDTO cartao

) {

    public CartaoCreditoResponseDTO(CartaoCredito entity) {
        this(entity.getId(), entity.getQuantidadeParcelas(), entity.getValorParcelas(), gerarCartaoResponseDTO(entity.getCartao()));
    }

    public static CartaoResponseDTO gerarCartaoResponseDTO(Cartao entity) {
        CartaoResponseDTO cartaoResponseDTO = new CartaoResponseDTO(entity);
        return cartaoResponseDTO;
    }

}
