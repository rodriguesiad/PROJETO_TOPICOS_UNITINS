package br.unitins.projeto.dto.tipo_produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TipoProdutoDTO(

    @NotBlank(message = "O campo descriçao deve ser informado.")
    @Size(max = 40, message = "O campo descrição deve possuir no máximo 40 caracteres.")
    String descricao

) {
  
}
