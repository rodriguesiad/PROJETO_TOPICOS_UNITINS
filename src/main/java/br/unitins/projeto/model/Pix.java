package br.unitins.projeto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Pix extends MetodoDePagamento {

    @Column(name = "chave", nullable = false)
    private String chave;

    @Column(name = "tipo_chave", nullable = false)
    private TipoChavePix tipoChavePix;

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public TipoChavePix getTipoChavePix() {
        return tipoChavePix;
    }

    public void setTipoChavePix(TipoChavePix tipoChavePix) {
        this.tipoChavePix = tipoChavePix;
    }

}
