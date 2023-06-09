package br.unitins.projeto.dto.pix;

import br.unitins.projeto.model.Pix;
import br.unitins.projeto.model.TipoChavePix;

public record PixResponseDTO(
        String chave,
        TipoChavePix tipoChavePix
) {

    public PixResponseDTO(Pix entity) {
        this(entity.getChave(), entity.getTipoChavePix());
    }

}