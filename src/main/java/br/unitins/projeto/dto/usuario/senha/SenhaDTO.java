package br.unitins.projeto.dto.usuario.senha;

import jakarta.validation.constraints.NotBlank;

public record SenhaDTO(

        @NotBlank String senhaAtual,

        @NotBlank String novaSenha

) {

}
