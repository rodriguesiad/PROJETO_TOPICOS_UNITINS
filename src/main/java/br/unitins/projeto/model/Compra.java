package br.unitins.projeto.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Compra extends DefaultEntity {

    @Column(name = "dataPagamento", columnDefinition = "TIMESTAMP", nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd 00:00:00.00", shape = JsonFormat.Shape.STRING)
    private LocalDateTime data;

    @Column(name = "total_compra", nullable = false)
    private Double totalCompra;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_endereco", nullable = false)
    private EnderecoCompra enderecoCompra;

    @Column(name = "status", nullable = false)
    private StatusCompra statusCompra;

    @OneToMany(mappedBy = "compra")
    private List<ItemCompra> itensCompra;

    @OneToMany(mappedBy = "compra")
    private List<HistoricoEntrega> historicoEntregra;

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(Double totalCompra) {
        this.totalCompra = totalCompra;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public EnderecoCompra getEnderecoCompra() {
        return enderecoCompra;
    }

    public void setEnderecoCompra(EnderecoCompra enderecoCompra) {
        this.enderecoCompra = enderecoCompra;
    }

    public StatusCompra getStatusCompra() {
        return statusCompra;
    }

    public void setStatusCompra(StatusCompra statusCompra) {
        this.statusCompra = statusCompra;
    }

    public List<ItemCompra> getItensCompra() {
        return itensCompra;
    }

    public void setItensCompra(List<ItemCompra> itensCompra) {
        this.itensCompra = itensCompra;
    }

    public List<HistoricoEntrega> getHistoricoEntregra() {
        return historicoEntregra;
    }

    public void setHistoricoEntregra(List<HistoricoEntrega> historicoEntregra) {
        this.historicoEntregra = historicoEntregra;
    }

}
