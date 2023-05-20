package br.unitins.projeto.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import br.unitins.projeto.model.Cartao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class CartaoRepository implements PanacheRepository<Cartao> {
    
    public List<Cartao> findByNumeroCartao(String numeroCartao){
        if (numeroCartao == null)
            return null;
        return find("UPPER(numeroCartao) LIKE ?1 ", "%"+numeroCartao.toUpperCase()+"%").list();
    }

}
