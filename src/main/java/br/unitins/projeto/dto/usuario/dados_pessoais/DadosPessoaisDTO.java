package br.unitins.projeto.dto.usuario.dados_pessoais;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.smallrye.common.constraint.NotNull;

public record DadosPessoaisDTO(

                @NotNull @JsonFormat(pattern = "yyyy-MM-dd") LocalDate dataNascimento) {

}
