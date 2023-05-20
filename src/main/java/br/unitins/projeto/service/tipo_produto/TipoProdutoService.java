package br.unitins.projeto.service.tipo_produto;

import java.util.List;

import br.unitins.projeto.dto.tipo_produto.TipoProdutoDTO;
import br.unitins.projeto.dto.tipo_produto.TipoProdutoResponseDTO;

public interface TipoProdutoService {

    List<TipoProdutoResponseDTO> getAll();

    TipoProdutoResponseDTO findById(Long id);

    TipoProdutoResponseDTO create(TipoProdutoDTO productDTO);

    TipoProdutoResponseDTO update(Long id, TipoProdutoDTO productDTO);

    void delete(Long id);

    List<TipoProdutoResponseDTO> findByDescricao(String descricao);

    Long count();

}
