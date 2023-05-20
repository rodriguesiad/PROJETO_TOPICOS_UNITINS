package br.unitins.projeto.dto.artigo_ceramica;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.projeto.dto.artesao.ArtesaoResponseDTO;
import br.unitins.projeto.dto.tipo_produto.TipoProdutoResponseDTO;
import br.unitins.projeto.model.Artesao;
import br.unitins.projeto.model.ArtigoCeramica;
import br.unitins.projeto.model.TipoProduto;

public record ArtigoCeramicaResponseDTO(
        Long id,
        String nome,
        String descricao,
        Double preco,
        Integer estoque,
        Integer quantidadePecas,
        ArtesaoResponseDTO artesao,
        List<TipoProdutoResponseDTO> tipoProduto) {

    public ArtigoCeramicaResponseDTO(ArtigoCeramica entity) {
        this(entity.getId(), entity.getNome(), entity.getDescricao(), entity.getPreco(),
                entity.getEstoque(), entity.getQuantidadePecas(), gerarArtesaoDTO(entity.getArtesao()),
                gerarTipoProdutoDTO(entity.getTipoProduto()));
    }

    public static List<TipoProdutoResponseDTO> gerarTipoProdutoDTO(List<TipoProduto> list) {
        if (list != null)
            return list.stream().map(TipoProdutoResponseDTO::new).collect(Collectors.toList());
        return null;
    }

    public static ArtesaoResponseDTO gerarArtesaoDTO(Artesao artesao) {
        ArtesaoResponseDTO artesaoResponseDTO = new ArtesaoResponseDTO(artesao);
        return artesaoResponseDTO;
    }

}
