package br.unitins.projeto.dto.artesao;

import br.unitins.projeto.model.Artesao;

public record ArtesaoResponseDTO(
        Long id,
        String nome,
        String descricao) {

    public ArtesaoResponseDTO(Artesao entity) {
        this(entity.getId(), entity.getNome(), entity.getDescricao());
    }

}
