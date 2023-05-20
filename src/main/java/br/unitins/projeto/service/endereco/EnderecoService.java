package br.unitins.projeto.service.endereco;

import java.util.List;

import jakarta.validation.Valid;

import br.unitins.projeto.dto.endereco.EnderecoDTO;
import br.unitins.projeto.dto.endereco.EnderecoResponseDTO;
import br.unitins.projeto.model.Endereco;

public interface EnderecoService {

    List<EnderecoResponseDTO> getAll();

    EnderecoResponseDTO findById(Long id);

    EnderecoResponseDTO create(EnderecoDTO productDTO);

    EnderecoResponseDTO update(Long id, EnderecoDTO productDTO);

    Endereco toModel(@Valid EnderecoDTO enderecoDTO);

    void delete(Long id);

    List<EnderecoResponseDTO> findByCEP(String cep);

    Long count();

}