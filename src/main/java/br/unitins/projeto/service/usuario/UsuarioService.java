package br.unitins.projeto.service.usuario;

import java.util.List;

import br.unitins.projeto.dto.endereco.EnderecoDTO;
import br.unitins.projeto.dto.endereco.EnderecoResponseDTO;
import br.unitins.projeto.dto.usuario.UsuarioDTO;
import br.unitins.projeto.dto.usuario.UsuarioResponseDTO;
import br.unitins.projeto.dto.usuario.dados_pessoais.DadosPessoaisDTO;
import br.unitins.projeto.dto.usuario.dados_pessoais.DadosPessoaisResponseDTO;
import br.unitins.projeto.dto.usuario.enderecos.UsuarioEnderecoDTO;
import br.unitins.projeto.dto.usuario.enderecos.UsuarioEnderecoResponseDTO;
import br.unitins.projeto.dto.usuario.senha.SenhaDTO;
import br.unitins.projeto.dto.usuario.telefone.UsuarioTelefoneDTO;
import br.unitins.projeto.dto.usuario.telefone.UsuarioTelefoneResponseDTO;
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

    DadosPessoaisResponseDTO getDadosPessoais(Long id);

    DadosPessoaisResponseDTO updateDadosPessoais(Long id, DadosPessoaisDTO dto);

    Boolean updateSenha(Long id, SenhaDTO senha);

    UsuarioEnderecoResponseDTO getEnderecos(Long id);

    UsuarioEnderecoResponseDTO updateEnderecos(Long id, UsuarioEnderecoDTO dto);

    EnderecoResponseDTO insertEndereco(Long id, EnderecoDTO dto);

    UsuarioTelefoneResponseDTO getTelefone(Long id);

    UsuarioTelefoneResponseDTO updateTelefone(Long id, UsuarioTelefoneDTO dto);

    UsuarioResponseDTO update(Long id, String nomeImagem);

}
