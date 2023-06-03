package br.unitins.projeto.resource;

import br.unitins.projeto.application.Result;
import br.unitins.projeto.dto.cartao.CartaoDTO;
import br.unitins.projeto.dto.cartao.CartaoResponseDTO;
import br.unitins.projeto.dto.compra.CompraResponseDTO;
import br.unitins.projeto.dto.endereco.EnderecoDTO;
import br.unitins.projeto.dto.endereco.EnderecoResponseDTO;
import br.unitins.projeto.dto.usuario.UsuarioResponseDTO;
import br.unitins.projeto.dto.usuario.cartoes.UsuarioCartaoResponseDTO;
import br.unitins.projeto.dto.usuario.dados_pessoais.DadosPessoaisDTO;
import br.unitins.projeto.dto.usuario.dados_pessoais.DadosPessoaisResponseDTO;
import br.unitins.projeto.dto.usuario.enderecos.UsuarioEnderecoResponseDTO;
import br.unitins.projeto.dto.usuario.senha.SenhaDTO;
import br.unitins.projeto.dto.usuario.telefone.UsuarioTelefoneDTO;
import br.unitins.projeto.dto.usuario.telefone.UsuarioTelefoneResponseDTO;
import br.unitins.projeto.form.ImageForm;
import br.unitins.projeto.service.compra.CompraService;
import br.unitins.projeto.service.file.FileService;
import br.unitins.projeto.service.usuario.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import java.io.IOException;
import java.util.List;

@Path("/usuariologado")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioLogadoResource {

    @Inject
    UsuarioService service;

    @Inject
    CompraService compraService;

    @Inject
    JsonWebToken jwt;

    @Inject
    FileService fileService;


    private Long getIdUsuario() {
        String login = jwt.getSubject();
        UsuarioResponseDTO usuario = service.findByLogin(login);
        return usuario.id();
    }

    @GET
    @RolesAllowed({"Admin", "User"})
    public Response getPerfilUsuario() {
        String login = jwt.getSubject();
        UsuarioResponseDTO usuario = service.findByLogin(login);

        return Response.ok(usuario).build();
    }

    @GET
    @Path("/dadosPessoais")
    @RolesAllowed({"Admin", "User"})
    public Response getDadosPessoais() {
        try {
            DadosPessoaisResponseDTO response = service.getDadosPessoais(this.getIdUsuario());
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PATCH
    @Path("/dadosPessoais")
    @RolesAllowed({"Admin", "User"})
    public Response setDadosPessoais(@Valid DadosPessoaisDTO dto) {
        try {
            DadosPessoaisResponseDTO response = service.updateDadosPessoais(this.getIdUsuario(), dto);
            return Response.ok(response).build();

        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PATCH
    @Path("/alterarSenha")
    @RolesAllowed({"Admin", "User"})
    public Response alterarSenha(@Valid SenhaDTO dto) {
        try {
            Boolean response = service.updateSenha(this.getIdUsuario(), dto);
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @GET
    @Path("/enderecos")
    @RolesAllowed({"Admin", "User"})
    public Response getEnderecos() {
        try {
            UsuarioEnderecoResponseDTO response = service.getEnderecos(this.getIdUsuario());
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PATCH
    @Path("/enderecos/{idEndereco}")
    @RolesAllowed({"Admin", "User"})
    public Response updateEnderecos(@PathParam("idEndereco") Long idEndereco, @Valid EnderecoDTO dto) {
        try {
            UsuarioEnderecoResponseDTO response = service.updateEndereco(this.getIdUsuario(), idEndereco, dto);
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/enderecos/{idEndereco}")
    @RolesAllowed({"Admin", "User"})
    public Response deleteEndereco(@PathParam("idEndereco") Long idEndereco) {
        service.deleteEndereco(this.getIdUsuario(), idEndereco);
        return Response.status(Status.NO_CONTENT).build();
    }

    @PATCH
    @Path("/cadastro-endereco")
    @RolesAllowed({"Admin", "User"})
    public Response insertEnderecos(@Valid EnderecoDTO dto) {
        try {
            EnderecoResponseDTO response = service.insertEndereco(this.getIdUsuario(), dto);
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @GET
    @Path("/cartoes")
    @RolesAllowed({"Admin", "User"})
    public Response getCartao() {
        try {
            UsuarioCartaoResponseDTO response = service.getCartoes(this.getIdUsuario());
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PATCH
    @Path("/cartoes/{idCartao}")
    @RolesAllowed({"Admin", "User"})
    public Response updateCartao(@PathParam("idCartao") Long idCartao, @Valid CartaoDTO dto) {
        try {
            UsuarioCartaoResponseDTO response = service.updateCartao(this.getIdUsuario(), idCartao, dto);
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/cartoes/{idCartao}")
    @RolesAllowed({"Admin", "User"})
    public Response deleteCartao(@PathParam("idCartao") Long idCartao) {
        service.deleteCartao(this.getIdUsuario(), idCartao);
        return Response.status(Status.NO_CONTENT).build();
    }

    @PATCH
    @Path("/cadastro-cartao")
    @RolesAllowed({"Admin", "User"})
    public Response insertCartao(@Valid CartaoDTO dto) {
        try {
            CartaoResponseDTO response = service.insertCartao(this.getIdUsuario(), dto);
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }


    @GET
    @Path("/contatos")
    @RolesAllowed({"Admin", "User"})
    public Response getContatos() {
        try {
            UsuarioTelefoneResponseDTO response = service.getTelefone(this.getIdUsuario());
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PATCH
    @Path("/contatos")
    @RolesAllowed({"Admin", "User"})
    public Response alterarContatos(@Valid UsuarioTelefoneDTO dto) {
        try {
            UsuarioTelefoneResponseDTO response = service.updateTelefone(this.getIdUsuario(), dto);
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PATCH
    @Path("/novaimagem")
    @RolesAllowed({"Admin", "User"})
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response salvarImagem(@MultipartForm ImageForm form) {
        String nomeImagem = "";
        Long id = this.getIdUsuario();

        try {
            nomeImagem = fileService.salvarImagem(form.getImagem(), form.getNomeImagem(), "usuario", jwt.getSubject());
        } catch (IOException e) {
            Result result = new Result(e.getMessage());
            return Response.status(Status.CONFLICT).entity(result).build();
        }

        UsuarioResponseDTO usuario = service.update(id, nomeImagem);
        return Response.ok(usuario).build();
    }

    @GET
    @Path("/download/{nomeImagem}")
    @RolesAllowed({"Admin", "User"})
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("nomeImagem") String nomeImagem) {
        Response.ResponseBuilder response = Response.ok(fileService.download(nomeImagem, "usuario", jwt.getSubject()));
        response.header("Content-Disposition", "attachment;filename=" + nomeImagem);

        return response.build();
    }

    @GET
    @Path("/minhas-compras")
    @RolesAllowed({"Admin", "User"})
    public Response minhasCompras() {
        try {
            List<CompraResponseDTO> response = compraService.findByUsuario(this.getIdUsuario());
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

}
