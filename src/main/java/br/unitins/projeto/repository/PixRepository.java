package br.unitins.projeto.repository;

import br.unitins.projeto.model.Pix;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PixRepository implements PanacheRepository<Pix> {

}
