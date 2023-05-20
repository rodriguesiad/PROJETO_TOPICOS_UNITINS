package br.unitins.projeto.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import br.unitins.projeto.model.TipoProduto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class TipoProdutoRepository implements PanacheRepository<TipoProduto> {
    
    public List<TipoProduto> findByDescricao(String descricao){
        if (descricao == null)
            return null;
        return find("UPPER(descricao) LIKE ?1 ", "%"+descricao.toUpperCase()+"%").list();
    }

}
