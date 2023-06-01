package br.unitins.projeto.dto.usuario.cartoes;

import br.unitins.projeto.dto.cartao.CartaoResponseDTO;
import br.unitins.projeto.dto.endereco.EnderecoResponseDTO;
import br.unitins.projeto.model.Cartao;
import br.unitins.projeto.model.Endereco;
import br.unitins.projeto.model.Usuario;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

public record UsuarioCartaoResponseDTO(

        @Valid List<CartaoResponseDTO> listaCartoes

) {

    public static UsuarioCartaoResponseDTO valueOf(Usuario entity) {
        return new UsuarioCartaoResponseDTO(gerarCartaoDTO(entity.getListaCartao()));
    }

    public static List<CartaoResponseDTO> gerarCartaoDTO(List<Cartao> list) {
        if (list != null)
            return list.stream().map(CartaoResponseDTO::new).collect(Collectors.toList());
        return null;
    }

}