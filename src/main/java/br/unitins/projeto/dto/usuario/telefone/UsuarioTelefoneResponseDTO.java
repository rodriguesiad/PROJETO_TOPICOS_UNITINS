package br.unitins.projeto.dto.usuario.telefone;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.unitins.projeto.dto.telefone.TelefoneResponseDTO;
import br.unitins.projeto.model.Telefone;
import br.unitins.projeto.model.Usuario;

public record UsuarioTelefoneResponseDTO(
        @JsonInclude(JsonInclude.Include.NON_NULL) TelefoneResponseDTO telefone,
        @JsonInclude(JsonInclude.Include.NON_NULL) TelefoneResponseDTO whatsapp) {

    public static UsuarioTelefoneResponseDTO valueOf(Usuario entity) {
        return new UsuarioTelefoneResponseDTO(gerarTelefoneDTO(entity.getTelefone()),
                gerarTelefoneDTO(entity.getWhastapp()));
    }

    public static TelefoneResponseDTO gerarTelefoneDTO(Telefone telefone) {
        if (telefone == null) {
            return null;
        }

        TelefoneResponseDTO telefoneResponseDTO = new TelefoneResponseDTO(telefone);
        return telefoneResponseDTO;
    }
}
