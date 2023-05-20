package br.unitins.projeto.service.usuario;

import java.util.List;

import br.unitins.projeto.dto.usuario.UsuarioDTO;
import br.unitins.projeto.dto.usuario.UsuarioResponseDTO;
import br.unitins.projeto.model.Usuario;

public interface UsuarioService {

    List<UsuarioResponseDTO> getAll();

    UsuarioResponseDTO findById(Long id);

    UsuarioResponseDTO create(UsuarioDTO productDTO);

    UsuarioResponseDTO update(Long id, UsuarioDTO productDTO);

    void delete(Long id);

    List<UsuarioResponseDTO> findByNome(String nome);

    Long count();

    Usuario findByLoginAndSenha(String login, String senha);

    UsuarioResponseDTO findByLogin(String login);

}
