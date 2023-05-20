package br.unitins.projeto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class TipoProduto extends DefaultEntity {

    @Column(nullable = false, length = 40)
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
