package br.unitins.projeto.dto.usuario;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.unitins.projeto.dto.artigo_ceramica.ArtigoCeramicaResponseDTO;
import br.unitins.projeto.dto.cartao.CartaoResponseDTO;
import br.unitins.projeto.dto.endereco.EnderecoResponseDTO;
import br.unitins.projeto.dto.telefone.TelefoneResponseDTO;
import br.unitins.projeto.model.ArtigoCeramica;
import br.unitins.projeto.model.Cartao;
import br.unitins.projeto.model.Endereco;
import br.unitins.projeto.model.Telefone;
import br.unitins.projeto.model.Usuario;

public record UsuarioResponseDTO(

        Long id,

        String nome,

        String email,

        String cpf,

        String senha,

        String login,

        TelefoneResponseDTO telefone,

        TelefoneResponseDTO whatsapp,

        List<EnderecoResponseDTO> enderecos,

        @JsonInclude(JsonInclude.Include.NON_NULL) List<CartaoResponseDTO> cartoes,

        @JsonInclude(JsonInclude.Include.NON_NULL) List<ArtigoCeramicaResponseDTO> listaDesejo

) {

    public static UsuarioResponseDTO valueOf(Usuario entity) {
        if (entity.getPessoaFisica() == null)
            return new UsuarioResponseDTO(entity.getId(),
                    null,
                    null,
                    null,
                    entity.getSenha(),
                    entity.getLogin(),
                    gerarTelefoneDTO(entity.getTelefone()),
                    gerarTelefoneDTO(entity.getWhastapp()),
                    gerarEnderecoDTO(entity.getListaEndereco()),
                    gerarCartaoDTO(entity.getCartoes()),
                    gerarListaDesejoDTO(entity.getListaDesejo()));

        return new UsuarioResponseDTO(entity.getId(),
                entity.getPessoaFisica().getCpf(),
                entity.getPessoaFisica().getNome(),
                entity.getPessoaFisica().getEmail(),
                entity.getSenha(),
                entity.getLogin(),
                gerarTelefoneDTO(entity.getTelefone()),
                gerarTelefoneDTO(entity.getWhastapp()),
                gerarEnderecoDTO(entity.getListaEndereco()),
                gerarCartaoDTO(entity.getCartoes()),
                gerarListaDesejoDTO(entity.getListaDesejo()));
    }

    public static List<CartaoResponseDTO> gerarCartaoDTO(List<Cartao> list) {
        if (list != null)
            return list.stream().map(CartaoResponseDTO::new).collect(Collectors.toList());
        return null;
    }

    public static List<EnderecoResponseDTO> gerarEnderecoDTO(List<Endereco> list) {
        if (list != null)
            return list.stream().map(EnderecoResponseDTO::new).collect(Collectors.toList());
        return null;
    }

    public static List<ArtigoCeramicaResponseDTO> gerarListaDesejoDTO(List<ArtigoCeramica> list) {
        if (list != null)
            return list.stream().map(ArtigoCeramicaResponseDTO::new).collect(Collectors.toList());
        return null;
    }

    public static TelefoneResponseDTO gerarTelefoneDTO(Telefone telefone) {
        TelefoneResponseDTO telefoneResponseDTO = new TelefoneResponseDTO(telefone);
        return telefoneResponseDTO;
    }
}