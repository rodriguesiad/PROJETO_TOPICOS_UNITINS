package br.unitins.projeto.dto.cartao;

import br.unitins.projeto.model.Cartao;

public record CartaoResponseDTO(
        Long id,

        String numeroCartao,

        String nomeTitular
) {

    public CartaoResponseDTO(Cartao entity) {
        this(entity.getId(), entity.getNumeroCartao(), entity.getNomeTitular());
    }

}
