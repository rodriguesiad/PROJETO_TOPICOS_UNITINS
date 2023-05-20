package br.unitins.projeto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Cartao extends DefaultEntity {

    @Column(nullable = false, length = 16)
    private String numeroCartao;

    @Column(nullable = false, length = 60)
    private String nomeTitular;

    @Column(nullable = false, length = 20)
    private String hash;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    public Usuario usuario;

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
