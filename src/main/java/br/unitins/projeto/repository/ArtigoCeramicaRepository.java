package br.unitins.projeto.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import br.unitins.projeto.model.ArtigoCeramica;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class ArtigoCeramicaRepository implements PanacheRepository<ArtigoCeramica> {
    
    public List<ArtigoCeramica> findByNome(String nome){
        if (nome == null)
            return null;
        return find("UPPER(nome) LIKE ?1 ", "%"+nome.toUpperCase()+"%").list();
    }

}
