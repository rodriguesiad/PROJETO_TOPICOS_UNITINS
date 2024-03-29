package br.unitins.projeto.service.usuario;

import br.unitins.projeto.dto.cartao.CartaoDTO;
import br.unitins.projeto.dto.cartao.CartaoResponseDTO;
import br.unitins.projeto.dto.cartao.CartaoUpdateDTO;
import br.unitins.projeto.dto.endereco.EnderecoDTO;
import br.unitins.projeto.dto.endereco.EnderecoResponseDTO;
import br.unitins.projeto.dto.endereco.EnderecoUpdateDTO;
import br.unitins.projeto.dto.usuario.UsuarioDTO;
import br.unitins.projeto.dto.usuario.UsuarioResponseDTO;
import br.unitins.projeto.dto.usuario.cartoes.UsuarioCartaoResponseDTO;
import br.unitins.projeto.dto.usuario.dados_pessoais.DadosPessoaisDTO;
import br.unitins.projeto.dto.usuario.dados_pessoais.DadosPessoaisResponseDTO;
import br.unitins.projeto.dto.usuario.enderecos.UsuarioEnderecoResponseDTO;
import br.unitins.projeto.dto.usuario.lista_desejo.ListaDesejoDTO;
import br.unitins.projeto.dto.usuario.lista_desejo.UsuarioListaDesejoResponseDTO;
import br.unitins.projeto.dto.usuario.senha.SenhaDTO;
import br.unitins.projeto.dto.usuario.telefone.UsuarioTelefoneDTO;
import br.unitins.projeto.dto.usuario.telefone.UsuarioTelefoneResponseDTO;
import br.unitins.projeto.model.Usuario;
import jakarta.validation.Valid;

import java.util.List;

public interface UsuarioService {

    List<UsuarioResponseDTO> getAll();

    UsuarioResponseDTO findById(Long id);

    UsuarioResponseDTO create(@Valid UsuarioDTO productDTO);

    void delete(Long id);

    List<UsuarioResponseDTO> findByNome(String nome);

    Long count();

    Usuario findByLoginAndSenha(String login, String senha);

    UsuarioResponseDTO findByLogin(String login);

    DadosPessoaisResponseDTO getDadosPessoais(Long id);

    DadosPessoaisResponseDTO updateDadosPessoais(Long id, @Valid DadosPessoaisDTO dto);

    Boolean updateSenha(Long id, @Valid SenhaDTO senha);

    UsuarioEnderecoResponseDTO getEnderecos(Long id);

    EnderecoResponseDTO insertEndereco(Long id, @Valid EnderecoDTO dto);

    UsuarioEnderecoResponseDTO updateEndereco(Long id, Long idEndereco, @Valid EnderecoUpdateDTO dto);

    void deleteEndereco(Long id, Long idEndereco);

    UsuarioCartaoResponseDTO getCartoes(Long id);

    CartaoResponseDTO insertCartao(Long id, @Valid CartaoDTO dto);

    UsuarioCartaoResponseDTO updateCartao(Long id, Long idCartao, @Valid CartaoUpdateDTO dto);

    void deleteCartao(Long id, Long idCartao);

    UsuarioTelefoneResponseDTO getTelefone(Long id);

    UsuarioTelefoneResponseDTO updateTelefone(Long id, @Valid UsuarioTelefoneDTO dto);

    UsuarioResponseDTO update(Long id, String nomeImagem);

    UsuarioListaDesejoResponseDTO getListaDesejo(Long id);

    UsuarioListaDesejoResponseDTO insertProdutoListaDesejo(Long id, @Valid ListaDesejoDTO dto);

    void deleteItemListaDesejo(Long id, Long idProduto);


}
