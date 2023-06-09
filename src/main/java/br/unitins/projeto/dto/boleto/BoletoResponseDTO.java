package br.unitins.projeto.dto.boleto;

import br.unitins.projeto.model.Boleto;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record BoletoResponseDTO(

        String numeroBoleto,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy 00:00:00")
        LocalDate vencimento

) {
    public BoletoResponseDTO(Boleto entity) {
        this(entity.getNumeroBoleto(), entity.getVencimento());
    }

}