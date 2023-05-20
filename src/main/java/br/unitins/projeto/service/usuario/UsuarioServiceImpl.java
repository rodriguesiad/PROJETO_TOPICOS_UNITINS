package br.unitins.projeto.service.usuario;

/*
 * import br.unitins.projeto.dto.cartao.CartaoDTO;
 * import br.unitins.projeto.dto.endereco.EnderecoDTO;
 * import br.unitins.projeto.dto.usuario.UsuarioDTO;
 * import br.unitins.projeto.dto.usuario.UsuarioResponseDTO;
 * import br.unitins.projeto.model.*;
 * import br.unitins.projeto.repository.ArtigoCeramicaRepository;
 * import br.unitins.projeto.repository.UsuarioRepository;
 * import br.unitins.projeto.service.cartao.CartaoService;
 * import br.unitins.projeto.service.endereco.EnderecoService;
 * import br.unitins.projeto.service.telefone.TelefoneService;
 * 
 * import jakarta.enterprise.context.ApplicationScoped;
 * import jakarta.inject.Inject;
 * import jakarta.transaction.Transactional;
 * import jakarta.validation.ConstraintViolation;
 * import jakarta.validation.ConstraintViolationException;
 * import jakarta.validation.Validator;
 * import jakarta.ws.rs.NotFoundException;
 * import java.util.ArrayList;
 * import java.util.List;
 * import java.util.Set;
 * import java.util.stream.Collectors;
 * 
 * @ApplicationScoped
 * public class UsuarioServiceImpl implements UsuarioService {
 * 
 * @Inject
 * UsuarioRepository repository;
 * 
 * @Inject
 * TelefoneService telefoneService;
 * 
 * @Inject
 * CartaoService cartaoService;
 * 
 * @Inject
 * EnderecoService enderecoService;
 * 
 * @Inject
 * ArtigoCeramicaRepository artigoCeramicaRepository;
 * 
 * @Inject
 * Validator validator;
 * 
 * @Override
 * public List<UsuarioResponseDTO> getAll() {
 * List<Usuario> list = repository.listAll();
 * return
 * list.stream().map(UsuarioResponseDTO::new).collect(Collectors.toList());
 * }
 * 
 * @Override
 * public UsuarioResponseDTO findById(Long id) {
 * Usuario usuario = repository.findById(id);
 * 
 * if (usuario == null)
 * throw new NotFoundException("Usuario não encontrado.");
 * 
 * return new UsuarioResponseDTO(usuario);
 * }
 * 
 * @Override
 * 
 * @Transactional
 * public UsuarioResponseDTO create(UsuarioDTO usuarioDTO) throws
 * ConstraintViolationException {
 * validar(usuarioDTO);
 * 
 * Usuario entity = new Usuario();
 * 
 * entity.setNome(usuarioDTO.nome());
 * entity.setEmail(usuarioDTO.email());
 * entity.setCpf(usuarioDTO.cpf());
 * entity.setSenha(usuarioDTO.senha());
 * 
 * entity.setTelefone(telefoneService.toModel(usuarioDTO.telefone()));
 * entity.setWhatsapp(telefoneService.toModel(usuarioDTO.whatsapp()));
 * 
 * // Endereços
 * if (usuarioDTO.enderecos() != null) {
 * List<Endereco> enderecos = new ArrayList<>();
 * 
 * for (EnderecoDTO endereco : usuarioDTO.enderecos()) {
 * Endereco enderecoModel = enderecoService.toModel(endereco);
 * enderecoModel.setUsuario(entity);
 * enderecos.add(enderecoModel);
 * }
 * 
 * entity.setEnderecos(enderecos);
 * }
 * 
 * // Cartões
 * if (usuarioDTO.cartoes() != null) {
 * 
 * List<Cartao> cartoes = new ArrayList<>();
 * 
 * for (CartaoDTO cartao : usuarioDTO.cartoes()) {
 * Cartao cartaoModel = cartaoService.toModel(cartao);
 * cartaoModel.setUsuario(entity);
 * cartoes.add(cartaoModel);
 * }
 * 
 * entity.setCartoes(cartoes);
 * }
 * 
 * // Lista de Desejos
 * if (usuarioDTO.listaDesejo() != null) {
 * List<ArtigoCeramica> artigos = new ArrayList<>();
 * 
 * for (Long item : usuarioDTO.listaDesejo()) {
 * artigos.add(artigoCeramicaRepository.findById(item));
 * }
 * 
 * entity.setListaDesejo(artigos);
 * }
 * 
 * repository.persist(entity);
 * 
 * return new UsuarioResponseDTO(entity);
 * }
 * 
 * @Override
 * 
 * @Transactional
 * public UsuarioResponseDTO update(Long id, UsuarioDTO usuarioDTO) throws
 * ConstraintViolationException {
 * validar(usuarioDTO);
 * 
 * Usuario entity = repository.findById(id);
 * 
 * entity.setNome(usuarioDTO.nome());
 * entity.setEmail(usuarioDTO.email());
 * entity.setCpf(usuarioDTO.cpf());
 * entity.setSenha(usuarioDTO.senha());
 * 
 * entity.setTelefone(new
 * Telefone(telefoneService.update(entity.getTelefone().getId(),
 * usuarioDTO.telefone())));
 * entity.setWhatsapp(new
 * Telefone(telefoneService.update(entity.getWhatsapp().getId(),usuarioDTO.
 * whatsapp())));
 * 
 * // Endereços
 * if (usuarioDTO.enderecos() != null) {
 * List<Endereco> enderecos = new ArrayList<>();
 * 
 * for (EnderecoDTO endereco : usuarioDTO.enderecos()) {
 * Endereco enderecoModel = enderecoService.toModel(endereco);
 * enderecoModel.setUsuario(entity);
 * enderecos.add(enderecoModel);
 * }
 * 
 * entity.setEnderecos(enderecos);
 * }
 * 
 * // Cartões
 * if (usuarioDTO.cartoes() != null) {
 * 
 * List<Cartao> cartoes = new ArrayList<>();
 * 
 * for (CartaoDTO cartao : usuarioDTO.cartoes()) {
 * Cartao cartaoModel = cartaoService.toModel(cartao);
 * cartaoModel.setUsuario(entity);
 * cartoes.add(cartaoModel);
 * }
 * 
 * entity.setCartoes(cartoes);
 * }
 * 
 * // Lista de Desejos
 * if (usuarioDTO.listaDesejo() != null) {
 * List<ArtigoCeramica> artigos = new ArrayList<>();
 * 
 * for (Long item : usuarioDTO.listaDesejo()) {
 * artigos.add(artigoCeramicaRepository.findById(item));
 * }
 * 
 * entity.setListaDesejo(artigos);
 * }
 * 
 * return new UsuarioResponseDTO(entity);
 * }
 * 
 * private void validar(UsuarioDTO usuarioDTO) throws
 * ConstraintViolationException {
 * Set<ConstraintViolation<UsuarioDTO>> violations =
 * validator.validate(usuarioDTO);
 * 
 * if (!violations.isEmpty())
 * throw new ConstraintViolationException(violations);
 * }
 * 
 * @Override
 * 
 * @Transactional
 * public void delete(Long id) {
 * repository.deleteById(id);
 * }
 * 
 * @Override
 * public List<UsuarioResponseDTO> findByNome(String nome) {
 * List<Usuario> list = repository.findByNome(nome);
 * return
 * list.stream().map(UsuarioResponseDTO::new).collect(Collectors.toList());
 * }
 * 
 * @Override
 * public Long count() {
 * return repository.count();
 * }
 * 
 * @Override
 * public Usuario findByLoginAndSenha(String login, String senha) {
 * return usuarioRepository.findByLoginAndSenha(login, senha);
 * }
 * 
 * @Override
 * public UsuarioResponseDTO findByLogin(String login) {
 * Usuario usuario = usuarioRepository.findByLogin(login);
 * if (usuario == null)
 * throw new NotFoundException("Usuário não encontrado.");
 * return UsuarioResponseDTO.valueOf(usuario);
 * }
 * 
 * }
 */