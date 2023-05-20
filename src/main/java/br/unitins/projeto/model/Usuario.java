package br.unitins.projeto.model;

import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.FetchType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Usuario extends DefaultEntity {

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senha;

    @ElementCollection
    @CollectionTable(name = "perfis", joinColumns = @JoinColumn(name = "id_usuario", referencedColumnName = "id"))
    @Column(name = "perfil", length = 30)
    private Set<Perfil> perfis;

    @OneToOne
    @JoinColumn(name = "id_telefone_celular", unique = true)
    private Telefone telefone;

    @OneToOne
    @JoinColumn(name = "id_telefone_whatsapp", unique = true)
    private Telefone whastapp;

    @OneToMany(mappedBy = "usuario")
    private List<Endereco> listaEndereco;

    @OneToOne
    @JoinColumn(name = "id_pessoa_fisica", unique = true, nullable = false)
    private PessoaFisica pessoaFisica;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cartao> cartoes;

    @ManyToMany
    @JoinTable(name = "lista_desejo", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_produto"))
    private List<ArtigoCeramica> listaDesejo;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<Perfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(Set<Perfil> perfis) {
        this.perfis = perfis;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public Telefone getWhastapp() {
        return whastapp;
    }

    public void setWhastapp(Telefone whastapp) {
        this.whastapp = whastapp;
    }

    public List<Endereco> getListaEndereco() {
        return listaEndereco;
    }

    public void setListaEndereco(List<Endereco> listaEndereco) {
        this.listaEndereco = listaEndereco;
    }

    public PessoaFisica getPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    public List<Cartao> getCartoes() {
        return cartoes;
    }

    public void setCartoes(List<Cartao> cartoes) {
        this.cartoes = cartoes;
    }

    public List<ArtigoCeramica> getListaDesejo() {
        return listaDesejo;
    }

    public void setListaDesejo(List<ArtigoCeramica> listaDesejo) {
        this.listaDesejo = listaDesejo;
    }

}
