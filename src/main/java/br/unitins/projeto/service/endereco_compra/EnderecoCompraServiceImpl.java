package br.unitins.projeto.service.endereco_compra;

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

import br.unitins.projeto.dto.endereco_compra.EnderecoCompraDTO;
import br.unitins.projeto.dto.endereco_compra.EnderecoCompraResponseDTO;
import br.unitins.projeto.model.EnderecoCompra;
import br.unitins.projeto.repository.MunicipioRepository;
import br.unitins.projeto.repository.EnderecoCompraRepository;

@ApplicationScoped
public class EnderecoCompraServiceImpl implements EnderecoCompraService {

    @Inject
    EnderecoCompraRepository repository;

    @Inject
    MunicipioRepository municipioRepository;

    @Inject
    Validator validator;

    @Override
    public List<EnderecoCompraResponseDTO> getAll() {
        List<EnderecoCompra> list = repository.listAll();
        return list.stream().map(EnderecoCompraResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public EnderecoCompraResponseDTO findById(Long id) {
        EnderecoCompra endereco = repository.findById(id);

        if (endereco == null)
            throw new NotFoundException("Endereço não encontrado.");

        return new EnderecoCompraResponseDTO(endereco);
    }

    @Override
    @Transactional
    public EnderecoCompraResponseDTO create(EnderecoCompraDTO enderecoCompraDTO) throws ConstraintViolationException {
        validar(enderecoCompraDTO);

        EnderecoCompra entity = new EnderecoCompra();

        entity.setLogradouro(enderecoCompraDTO.logradouro());
        entity.setBairro(enderecoCompraDTO.bairro());
        entity.setNumero(enderecoCompraDTO.numero());
        entity.setComplemento(enderecoCompraDTO.complemento());
        entity.setCep(enderecoCompraDTO.cep());
        entity.setMunicipio(municipioRepository.findById(enderecoCompraDTO.idMunicipio()));

        repository.persist(entity);

        return new EnderecoCompraResponseDTO(entity);
    }

    @Override
    @Transactional
    public EnderecoCompraResponseDTO update(Long id, EnderecoCompraDTO enderecoCompraDTO)
            throws ConstraintViolationException {
        validar(enderecoCompraDTO);

        EnderecoCompra entity = repository.findById(id);

        entity.setLogradouro(enderecoCompraDTO.logradouro());
        entity.setBairro(enderecoCompraDTO.bairro());
        entity.setNumero(enderecoCompraDTO.numero());
        entity.setComplemento(enderecoCompraDTO.complemento());
        entity.setCep(enderecoCompraDTO.cep());
        entity.setMunicipio(municipioRepository.findById(enderecoCompraDTO.idMunicipio()));

        return new EnderecoCompraResponseDTO(entity);
    }

    private void validar(EnderecoCompraDTO enderecoCompraDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<EnderecoCompraDTO>> violations = validator.validate(enderecoCompraDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<EnderecoCompraResponseDTO> findByCEP(String cep) {
        List<EnderecoCompra> list = repository.findByCEP(cep);
        return list.stream().map(EnderecoCompraResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long count() {
        return repository.count();
    }

}