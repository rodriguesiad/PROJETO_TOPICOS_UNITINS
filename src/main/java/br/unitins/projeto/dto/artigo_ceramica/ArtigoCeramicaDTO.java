package br.unitins.projeto.dto.artigo_ceramica;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ArtigoCeramicaDTO(

                @NotBlank(message = "O campo nome deve ser informado.") @Size(max = 50, message = "O campo nome deve possuir no máximo 60 caracteres.") String nome,

                @NotBlank(message = "O campo descrição deve ser informado.") @Size(max = 150, message = "O campo descrição deve possuir no máximo 60 caracteres.") String descricao,

                @NotNull(message = "O campo preço deve ser informado.") Double preco,

                @NotNull(message = "O campo estoque deve ser informado.") Integer estoque,

                @NotNull(message = "O campo quantidade de estoque deve ser informado.") Integer quantidadePecas,

                @NotNull(message = "O artesão deve ser informado.") Long idArtesao,

                @NotNull(message = "As tags de indentificação do produto devem ser informadas.") List<Long> tipoProduto

) {

}