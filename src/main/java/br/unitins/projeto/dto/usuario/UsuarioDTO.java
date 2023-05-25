package br.unitins.projeto.dto.usuario;

import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.unitins.projeto.dto.telefone.TelefoneDTO;

public record UsuarioDTO(

                @NotBlank(message = "O campo login deve ser informado.") String login,

                @NotBlank(message = "O campo senha deve ser informado.") String senha,

                @NotBlank(message = "O campo nome deve ser informado.") @Size(max = 60, message = "O campo nome deve possuir no máximo 60 caracteres.") String nome,

                @Email @NotBlank(message = "O campo e-mail deve ser informado.") String email,

                @CPF @NotBlank(message = "O campo CPF deve ser informado.") @Size(max = 11, min = 11, message = "O CPF deve ter 11 caracteres") String cpf,

                @JsonFormat(pattern = "yyyy-MM-dd") LocalDate dataNascimento,

                @NotNull(message = "O telefone deve ser informado.") @Valid TelefoneDTO telefone,

                @NotNull(message = "O whatsapp deve ser informado.") @Valid TelefoneDTO whatsapp

) {

}