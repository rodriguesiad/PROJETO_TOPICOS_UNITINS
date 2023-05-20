package br.unitins.projeto.service.artigo_ceramica;

import java.util.List;

import br.unitins.projeto.dto.artigo_ceramica.ArtigoCeramicaDTO;
import br.unitins.projeto.dto.artigo_ceramica.ArtigoCeramicaResponseDTO;

public interface ArtigoCeramicaService {

    List<ArtigoCeramicaResponseDTO> getAll();

    ArtigoCeramicaResponseDTO findById(Long id);

    ArtigoCeramicaResponseDTO create(ArtigoCeramicaDTO productDTO);

    ArtigoCeramicaResponseDTO update(Long id, ArtigoCeramicaDTO productDTO);

    void delete(Long id);

    List<ArtigoCeramicaResponseDTO> findByNome(String nome);

    Long count();

}
