package br.unitins.projeto.dto.tipo_produto;

import br.unitins.projeto.model.TipoProduto;

public record TipoProdutoResponseDTO(
        Long id,
        String descricao) {

    public TipoProdutoResponseDTO(TipoProduto entity) {
        this(entity.getId(), entity.getDescricao());
    }

}
