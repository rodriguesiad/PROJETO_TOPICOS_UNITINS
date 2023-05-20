package br.unitins.projeto.service.artesao;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

import br.unitins.projeto.dto.artesao.ArtesaoDTO;
import br.unitins.projeto.dto.artesao.ArtesaoResponseDTO;
import br.unitins.projeto.model.Artesao;
import br.unitins.projeto.repository.ArtesaoRepository;

@ApplicationScoped
public class ArtesaoServiceImpl implements ArtesaoService {

    @Inject
    ArtesaoRepository repository;

    @Inject
    Validator validator;

    @Override
    public List<ArtesaoResponseDTO> getAll() {
        List<Artesao> list = repository.listAll();
        return list.stream().map(ArtesaoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public ArtesaoResponseDTO findById(Long id) {
        Artesao artesao = repository.findById(id);

        if (artesao == null)
            throw new NotFoundException("Artesao não encontrado.");

        return new ArtesaoResponseDTO(artesao);
    }

    @Override
    @Transactional
    public ArtesaoResponseDTO create(ArtesaoDTO artesaoDTO) throws ConstraintViolationException {
        validar(artesaoDTO);

        Artesao entity = new Artesao();
        entity.setDescricao(artesaoDTO.descricao());
        entity.setNome(artesaoDTO.nome());
        repository.persist(entity);

        return new ArtesaoResponseDTO(entity);
    }

    @Override
    @Transactional
    public ArtesaoResponseDTO update(Long id, ArtesaoDTO artesaoDTO) throws ConstraintViolationException {
        validar(artesaoDTO);

        Artesao entity = repository.findById(id);
        entity.setDescricao(artesaoDTO.descricao());
        entity.setNome(artesaoDTO.nome());

        return new ArtesaoResponseDTO(entity);
    }

    private void validar(ArtesaoDTO artesaoDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<ArtesaoDTO>> violations = validator.validate(artesaoDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<ArtesaoResponseDTO> findByNome(String nome) {
        List<Artesao> list = repository.findByNome(nome);
        return list.stream().map(ArtesaoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long count() {
        return repository.count();
    }

}
