package br.unitins.projeto.dto.usuario.telefone;

import br.unitins.projeto.dto.telefone.TelefoneDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public record UsuarioTelefoneDTO(
        @NotEmpty @Valid TelefoneDTO telefone,
        @NotEmpty @Valid TelefoneDTO whatsapp

) {

}
