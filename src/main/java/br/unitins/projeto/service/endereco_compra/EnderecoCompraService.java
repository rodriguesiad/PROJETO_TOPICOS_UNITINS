package br.unitins.projeto.service.endereco_compra;

import java.util.List;

import br.unitins.projeto.dto.endereco_compra.EnderecoCompraDTO;
import br.unitins.projeto.dto.endereco_compra.EnderecoCompraResponseDTO;

public interface EnderecoCompraService {

    List<EnderecoCompraResponseDTO> getAll();

    EnderecoCompraResponseDTO findById(Long id);

    EnderecoCompraResponseDTO create(EnderecoCompraDTO productDTO);

    EnderecoCompraResponseDTO update(Long id, EnderecoCompraDTO productDTO);

    void delete(Long id);

    List<EnderecoCompraResponseDTO> findByCEP(String cep);

    Long count();

}