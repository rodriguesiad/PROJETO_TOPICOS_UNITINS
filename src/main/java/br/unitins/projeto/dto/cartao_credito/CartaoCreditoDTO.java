package br.unitins.projeto.dto.cartao_credito;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartaoCreditoDTO(

        @NotNull(message = "A quantidade de parcelas deve ser informada.")
        @Min(value = 1, message = "A quantidade de parcelas precisa ser maior q zero.")
        Integer quantidadeParcelas,

        @NotNull(message = "O cartão deve ser informado")
        Long idCartao

) {
}
