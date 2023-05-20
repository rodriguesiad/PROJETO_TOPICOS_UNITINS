package br.unitins.projeto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Artesao extends DefaultEntity {

    @Column(nullable = false, length = 40)
    private String nome;

    @Column(nullable = false, length = 150)
    private String descricao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
