package br.unitins.projeto.service.compra;

import br.unitins.projeto.dto.boleto.BoletoDTO;
import br.unitins.projeto.dto.boleto.BoletoResponseDTO;
import br.unitins.projeto.dto.cartao_credito.CartaoCreditoDTO;
import br.unitins.projeto.dto.cartao_credito.CartaoCreditoResponseDTO;
import br.unitins.projeto.dto.compra.CompraDTO;
import br.unitins.projeto.dto.compra.CompraResponseDTO;
import br.unitins.projeto.dto.compra.StatusCompraDTO;
import br.unitins.projeto.dto.historico_entrega.HistoricoEntregaDTO;
import br.unitins.projeto.dto.historico_entrega.HistoricoEntregaResponseDTO;
import br.unitins.projeto.dto.pix.PixDTO;
import br.unitins.projeto.dto.pix.PixResponseDTO;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;

import java.util.List;

public interface CompraService {

    List<CompraResponseDTO> getAll();

    CompraResponseDTO findById(Long id);

    CompraResponseDTO create(CompraDTO dto, Long idUsuario);

    CompraResponseDTO alterStatusCompra(Long idCompra, StatusCompraDTO dto);

    List<CompraResponseDTO> findByUsuario(Long idUsuario);

    List<HistoricoEntregaResponseDTO> getHistoricoEntrega(Long idCompra);

    HistoricoEntregaResponseDTO insertHistoricoEntrega(Long idCompra, @Valid HistoricoEntregaDTO dto);

    BoletoResponseDTO pagarPorBoleto(Long idCompra, @Valid BoletoDTO dto);

    PixResponseDTO pagarPorPix(Long idCompra, @Valid PixDTO dto);

    CartaoCreditoResponseDTO pagarPorCartao(Long idCompra, @Valid CartaoCreditoDTO dto);

    Response getMetodoPagamento(Long idCompra);

}
