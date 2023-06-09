package br.unitins.projeto.dto.usuario.lista_desejo;

import br.unitins.projeto.dto.artigo_ceramica.ArtigoCeramicaResponseDTO;
import br.unitins.projeto.model.ArtigoCeramica;
import br.unitins.projeto.model.Produto;
import br.unitins.projeto.model.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public record UsuarioListaDesejoResponseDTO(
        List<ArtigoCeramicaResponseDTO> listaDesejo
) {

    public static UsuarioListaDesejoResponseDTO valueOf(Usuario entity) {
        return new UsuarioListaDesejoResponseDTO(gerarListaDesejoDTO(entity.getListaDesejo()));
    }

    public static List<ArtigoCeramicaResponseDTO> gerarListaDesejoDTO(List<Produto> list) {
        if (list != null)
            return list.stream()
                    .map(produto -> (ArtigoCeramica) produto)
                    .map(ArtigoCeramicaResponseDTO::new)
                    .collect(Collectors.toList());
        return null;
    }

}