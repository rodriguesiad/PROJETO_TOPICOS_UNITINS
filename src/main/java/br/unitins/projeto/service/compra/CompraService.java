package br.unitins.projeto.service.compra;

import br.unitins.projeto.dto.compra.CompraDTO;
import br.unitins.projeto.dto.compra.CompraResponseDTO;
import br.unitins.projeto.dto.compra.StatusCompraDTO;
import br.unitins.projeto.dto.historico_entrega.HistoricoEntregaDTO;
import br.unitins.projeto.dto.historico_entrega.HistoricoEntregaResponseDTO;

import java.util.List;

public interface CompraService {

    List<CompraResponseDTO> getAll();

    CompraResponseDTO findById(Long id);

    CompraResponseDTO create(CompraDTO dto, Long idUsuario);

    CompraResponseDTO alterStatusCompra(Long idCompra, StatusCompraDTO dto);

    List<CompraResponseDTO> findByUsuario(Long idUsuario);

    List<HistoricoEntregaResponseDTO> getHistoricoEntrega(Long idCompra);

    HistoricoEntregaResponseDTO insertHistoricoEntrega(Long idCompra, HistoricoEntregaDTO dto);

}
