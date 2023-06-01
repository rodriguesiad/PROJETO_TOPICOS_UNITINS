package br.unitins.projeto.dto.cartao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CartaoDTO(

        Long id,
        @NotBlank(message = "O número do cartão deve ser informado") @Size(max = 16, message = "O número do cartão só pode ter até 16 caracteres.") String numeroCartao,

        @NotBlank(message = "O nome do titular deve ser informado") @Size(max = 100, message = "O nome do titular só pode ter até 100 caracteres.") String nomeTitular,

        @NotBlank(message = "O CVC deve ser informado") @Size(min = 3, max = 3, message = "O CVC só deve ter 3 caracteres.") String cvc,

        @NotBlank(message = "A data de vencimento deve ser informado") @Size(min = 4, max = 5, message = "A data de vencimento só deve ter entre 4 e 5 caracteres.") String dataVencimento

) {

}