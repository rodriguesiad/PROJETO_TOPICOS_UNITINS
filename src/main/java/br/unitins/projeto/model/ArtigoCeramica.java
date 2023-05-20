package br.unitins.projeto.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class ArtigoCeramica extends Produto {

    @ManyToMany
    @JoinTable(name = "artigo_tipo", joinColumns = @JoinColumn(name = "id_artigo"), inverseJoinColumns = @JoinColumn(name = "id_tipo"))
    private List<TipoProduto> TipoProduto = new ArrayList<>();

    @Column(nullable = false)
    private Integer quantidadePecas;

    @ManyToOne
    @JoinColumn(name = "id_artesao", nullable = false)
    private Artesao artesao;

    public List<TipoProduto> getTipoProduto() {
        return TipoProduto;
    }

    public void setTipoProduto(List<TipoProduto> tipoProduto) {
        TipoProduto = tipoProduto;
    }

    public Integer getQuantidadePecas() {
        return quantidadePecas;
    }

    public void setQuantidadePecas(Integer quantidadePecas) {
        this.quantidadePecas = quantidadePecas;
    }

    public Artesao getArtesao() {
        return artesao;
    }

    public void setArtesao(Artesao artesao) {
        this.artesao = artesao;
    }

}
