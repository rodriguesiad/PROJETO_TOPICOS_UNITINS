package br.unitins.projeto.dto.usuario.cartoes;

import br.unitins.projeto.dto.cartao.CartaoDTO;
import br.unitins.projeto.dto.endereco.EnderecoDTO;
import jakarta.validation.Valid;

import java.util.List;

public record UsuarioCartaoDTO(

        @Valid List<CartaoDTO> listaCartoes

) {

}
