package br.unitins.projeto.service.artesao;

import java.util.List;

import br.unitins.projeto.dto.artesao.ArtesaoDTO;
import br.unitins.projeto.dto.artesao.ArtesaoResponseDTO;

public interface ArtesaoService {

    List<ArtesaoResponseDTO> getAll();

    ArtesaoResponseDTO findById(Long id);

    ArtesaoResponseDTO create(ArtesaoDTO productDTO);

    ArtesaoResponseDTO update(Long id, ArtesaoDTO productDTO);

    void delete(Long id);

    List<ArtesaoResponseDTO> findByNome(String nome);

    Long count();

}
