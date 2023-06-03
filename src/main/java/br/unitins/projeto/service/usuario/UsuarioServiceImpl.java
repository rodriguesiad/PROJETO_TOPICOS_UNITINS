package br.unitins.projeto.service.usuario;

import br.unitins.projeto.dto.cartao.CartaoDTO;
import br.unitins.projeto.dto.cartao.CartaoResponseDTO;
import br.unitins.projeto.dto.endereco.EnderecoDTO;
import br.unitins.projeto.dto.endereco.EnderecoResponseDTO;
import br.unitins.projeto.dto.usuario.UsuarioDTO;
import br.unitins.projeto.dto.usuario.UsuarioResponseDTO;
import br.unitins.projeto.dto.usuario.cartoes.UsuarioCartaoResponseDTO;
import br.unitins.projeto.dto.usuario.dados_pessoais.DadosPessoaisDTO;
import br.unitins.projeto.dto.usuario.dados_pessoais.DadosPessoaisResponseDTO;
import br.unitins.projeto.dto.usuario.enderecos.UsuarioEnderecoResponseDTO;
import br.unitins.projeto.dto.usuario.senha.SenhaDTO;
import br.unitins.projeto.dto.usuario.telefone.UsuarioTelefoneDTO;
import br.unitins.projeto.dto.usuario.telefone.UsuarioTelefoneResponseDTO;
import br.unitins.projeto.model.Cartao;
import br.unitins.projeto.model.DefaultEntity;
import br.unitins.projeto.model.Endereco;
import br.unitins.projeto.model.Perfil;
import br.unitins.projeto.model.PessoaFisica;
import br.unitins.projeto.model.Usuario;
import br.unitins.projeto.repository.ArtigoCeramicaRepository;
import br.unitins.projeto.repository.UsuarioRepository;
import br.unitins.projeto.service.cartao.CartaoService;
import br.unitins.projeto.service.endereco.EnderecoService;
import br.unitins.projeto.service.hash.HashService;
import br.unitins.projeto.service.telefone.TelefoneService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository repository;

    @Inject
    TelefoneService telefoneService;

    @Inject
    CartaoService cartaoService;

    @Inject
    EnderecoService enderecoService;

    @Inject
    ArtigoCeramicaRepository artigoCeramicaRepository;

    @Inject
    Validator validator;

    @Inject
    HashService hashService;

    @Override
    public List<UsuarioResponseDTO> getAll() {
        List<Usuario> list = repository.listAll();
        return list.stream().map(usuario -> UsuarioResponseDTO.valueOf(usuario)).collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO findById(Long id) {
        Usuario usuario = this.getUsuario(id);

        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO create(@Valid UsuarioDTO usuarioDTO) throws ConstraintViolationException {

        validar(usuarioDTO);

        Usuario entity = new Usuario();
        PessoaFisica pessoa = new PessoaFisica();

        pessoa.setNome(usuarioDTO.nome());
        pessoa.setEmail(usuarioDTO.email());
        pessoa.setCpf(usuarioDTO.cpf());
        pessoa.setDataNascimento(usuarioDTO.dataNascimento());

        entity.setLogin(usuarioDTO.login());
        entity.setSenha(hashService.getHashSenha(usuarioDTO.senha()));

        entity.setTelefone(telefoneService.toModel(usuarioDTO.telefone()));
        entity.setWhatsapp(telefoneService.toModel(usuarioDTO.whatsapp()));

        entity.setPessoaFisica(pessoa);

        Set<Perfil> perfis = new HashSet<>();
        perfis.add(Perfil.USER);
        entity.setPerfis(perfis);

        repository.persist(entity);

        return UsuarioResponseDTO.valueOf(entity);
    }


    private void validar(UsuarioDTO usuarioDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(usuarioDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<UsuarioResponseDTO> findByNome(String nome) {
        List<Usuario> list = repository.findByNome(nome);
        return list.stream().map(usuario -> UsuarioResponseDTO.valueOf(usuario)).collect(Collectors.toList());

    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public Usuario findByLoginAndSenha(String login, String senha) {
        return repository.findByLoginAndSenha(login, senha);
    }

    @Override
    public UsuarioResponseDTO findByLogin(String login) {
        Usuario usuario = repository.findByLogin(login);
        if (usuario == null)
            throw new NotFoundException("Usuário não encontrado.");
        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Override
    public DadosPessoaisResponseDTO getDadosPessoais(Long id) {
        Usuario usuario = this.getUsuario(id);

        return DadosPessoaisResponseDTO.valueOf(usuario);
    }

    @Override
    @Transactional
    public DadosPessoaisResponseDTO updateDadosPessoais(Long id, @Valid DadosPessoaisDTO dto) {
        Usuario usuario = this.getUsuario(id);

        try {
            PessoaFisica pessoa = usuario.getPessoaFisica();
            pessoa.setDataNascimento(dto.dataNascimento());
            usuario.setPessoaFisica(pessoa);

            return DadosPessoaisResponseDTO.valueOf(usuario);
        } catch (NullPointerException e) {
            throw new NullPointerException("O usuário não possui dados pessoais");
        }
    }

    @Override
    @Transactional
    public Boolean updateSenha(Long id, @Valid SenhaDTO dto) {
        Usuario usuario = this.getUsuario(id);

        try {
            if (usuario.getSenha().equals(hashService.getHashSenha(dto.senhaAtual()))) {
                usuario.setSenha(hashService.getHashSenha(dto.novaSenha()));
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            throw new NullPointerException("O usuário não possui dados pessoais");
        }
    }

    @Override
    public UsuarioEnderecoResponseDTO getEnderecos(Long id) {
        Usuario usuario = this.getUsuario(id);

        return UsuarioEnderecoResponseDTO.valueOf(usuario);
    }


    @Override
    @Transactional
    public EnderecoResponseDTO insertEndereco(Long id, @Valid EnderecoDTO dto) {
        Usuario usuario = this.getUsuario(id);

        if (usuario.getListaEndereco().isEmpty()) {
            usuario.setListaEndereco(new ArrayList<>());
        }

        Endereco endereco = enderecoService.toModel(dto);
        endereco.setUsuario(usuario);
        enderecoService.create(endereco);

        usuario.getListaEndereco().add(endereco);

        return new EnderecoResponseDTO(endereco);
    }

    @Override
    @Transactional
    public UsuarioEnderecoResponseDTO updateEndereco(Long id, Long idEndereco, @Valid EnderecoDTO dto) {
        Usuario usuario = this.getUsuario(id);

        if (usuario.getListaEndereco().isEmpty()) {
            throw new NotFoundException("O usuário não possuiu endereços cadastrados.");
        }

        int index = usuario.getListaEndereco().stream()
                .map(DefaultEntity::getId)
                .toList().indexOf(idEndereco);

        if (index == -1)
            throw new NotFoundException("Endereço não encontrado");

        Endereco enderecoModel = enderecoService.toModel(dto);
        enderecoService.update(dto.id(), dto);
        usuario.getListaEndereco().set(index, enderecoModel);

        return UsuarioEnderecoResponseDTO.valueOf(usuario);
    }

    @Override
    @Transactional
    public void deleteEndereco(Long id, Long idEndereco) {
        Usuario usuario = this.getUsuario(id);

        if (usuario.getListaEndereco().isEmpty()) {
            throw new NotFoundException("O usuário não possuiu endereços cadastrados.");
        }

        int index = usuario.getListaEndereco().stream()
                .map(DefaultEntity::getId)
                .toList().indexOf(idEndereco);

        if (index == -1)
            throw new NotFoundException("Endereço não encontrado");

        enderecoService.delete(idEndereco);
        usuario.getListaEndereco().remove(index);
    }

    @Override
    public UsuarioCartaoResponseDTO getCartoes(Long id) {
        Usuario usuario = this.getUsuario(id);

        return UsuarioCartaoResponseDTO.valueOf(usuario);
    }

    @Override
    @Transactional
    public CartaoResponseDTO insertCartao(Long id, @Valid CartaoDTO dto) {
        Usuario usuario = this.getUsuario(id);

        if (usuario.getListaCartao().isEmpty()) {
            usuario.setListaCartao(new ArrayList<>());
        }

        Cartao cartao = cartaoService.toModel(dto);
        cartao.setUsuario(usuario);
        cartaoService.create(cartao);

        usuario.getListaCartao().add(cartao);

        return new CartaoResponseDTO(cartao);
    }

    @Override
    @Transactional
    public UsuarioCartaoResponseDTO updateCartao(Long id, Long idCartao, @Valid CartaoDTO dto) {
        Usuario usuario = this.getUsuario(id);

        if (usuario.getListaCartao().isEmpty()) {
            throw new NotFoundException("O usuário não possuiu cartões cadastrados.");
        }

        int index = usuario.getListaCartao().stream()
                .map(DefaultEntity::getId)
                .toList().indexOf(idCartao);

        if (index == -1)
            throw new NotFoundException("Cartão não encontrado");

        Cartao cartaoModel = cartaoService.toModel(dto);
        cartaoService.update(dto.id(), dto);
        usuario.getListaCartao().set(index, cartaoModel);

        return UsuarioCartaoResponseDTO.valueOf(usuario);
    }

    @Override
    @Transactional
    public void deleteCartao(Long id, Long idCartao) {
        Usuario usuario = this.getUsuario(id);

        if (usuario.getListaCartao().isEmpty()) {
            throw new NotFoundException("O usuário não possuiu cartões cadastrados.");
        }

        int index = usuario.getListaCartao().stream()
                .map(DefaultEntity::getId)
                .toList().indexOf(idCartao);

        if (index == -1)
            throw new NotFoundException("Cartão não encontrado");

        cartaoService.delete(idCartao);
        usuario.getListaCartao().remove(index);
    }

    @Override
    public UsuarioTelefoneResponseDTO getTelefone(Long id) {
        Usuario usuario = this.getUsuario(id);

        return UsuarioTelefoneResponseDTO.valueOf(usuario);
    }

    @Override
    @Transactional
    public UsuarioTelefoneResponseDTO updateTelefone(Long id, @Valid UsuarioTelefoneDTO dto) {
        Usuario usuario = this.getUsuario(id);

        try {
            usuario.setTelefone(telefoneService.toModel(dto.telefone()));
            usuario.setWhatsapp(telefoneService.toModel(dto.whatsapp()));

            return UsuarioTelefoneResponseDTO.valueOf(usuario);
        } catch (NullPointerException e) {
            throw new NullPointerException("O usuário não possui dados pessoais");
        }
    }

    @Override
    @Transactional
    public UsuarioResponseDTO update(Long id, String nomeImagem) {
        Usuario entity = repository.findById(id);
        entity.setNomeImagem(nomeImagem);

        return UsuarioResponseDTO.valueOf(entity);
    }

    private Usuario getUsuario(Long id) {
        Usuario usuario = repository.findById(id);

        if (usuario == null)
            throw new NotFoundException("Usuário não encontrado.");

        return usuario;
    }

}
