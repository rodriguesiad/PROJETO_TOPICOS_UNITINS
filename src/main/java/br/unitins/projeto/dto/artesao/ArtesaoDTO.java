package br.unitins.projeto.dto.artesao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ArtesaoDTO(

                @NotBlank(message = "O campo nome deve ser informado.") @Size(max = 40, message = "O campo nome deve possuir no máximo 60 caracteres.") String nome,

                @NotBlank(message = "O campo descrição deve ser informado.") @Size(max = 60, message = "O campo descrição deve possuir no máximo 60 caracteres.") String descricao

) {

}
