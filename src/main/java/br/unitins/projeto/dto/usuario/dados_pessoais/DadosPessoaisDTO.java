package br.unitins.projeto.dto.usuario.dados_pessoais;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.smallrye.common.constraint.NotNull;

public record DadosPessoaisDTO(

        @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") LocalDate dataNascimento) {

}
