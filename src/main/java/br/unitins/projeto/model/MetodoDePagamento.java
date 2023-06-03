package br.unitins.projeto.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public abstract class MetodoDePagamento extends DefaultEntity {

    @ManyToOne
    @JoinColumn(name = "id_compra", nullable = false)
    public Compra compra;

}
