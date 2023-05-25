package br.unitins.projeto.resource;

import br.unitins.projeto.dto.endereco.EnderecoDTO;
import br.unitins.projeto.dto.endereco.EnderecoResponseDTO;
import br.unitins.projeto.form.ImageForm;
import br.unitins.projeto.service.file.FileService;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.projeto.application.Result;
import br.unitins.projeto.dto.usuario.UsuarioResponseDTO;
import br.unitins.projeto.dto.usuario.dados_pessoais.DadosPessoaisDTO;
import br.unitins.projeto.dto.usuario.dados_pessoais.DadosPessoaisResponseDTO;
import br.unitins.projeto.dto.usuario.enderecos.UsuarioEnderecoDTO;
import br.unitins.projeto.dto.usuario.enderecos.UsuarioEnderecoResponseDTO;
import br.unitins.projeto.dto.usuario.senha.SenhaDTO;
import br.unitins.projeto.dto.usuario.telefone.UsuarioTelefoneDTO;
import br.unitins.projeto.dto.usuario.telefone.UsuarioTelefoneResponseDTO;
import br.unitins.projeto.service.usuario.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;;import java.io.IOException;

@Path("/usuariologado")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioLogadoResource {

    @Inject
    UsuarioService service;

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
    @RolesAllowed({ "Admin", "User" })
    public Response getPerfilUsuario() {
        String login = jwt.getSubject();
        UsuarioResponseDTO usuario = service.findByLogin(login);

        return Response.ok(usuario).build();
    }

    @GET
    @Path("/dadosPessoais")
    @RolesAllowed({ "Admin", "User" })
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
    @RolesAllowed({ "Admin", "User" })
    public Response setDadosPessoais(DadosPessoaisDTO dto) {
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
    @RolesAllowed({ "Admin", "User" })
    public Response alterarSenha(SenhaDTO dto) {
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
    @RolesAllowed({ "Admin", "User" })
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
    @Path("/enderecos")
    @RolesAllowed({ "Admin", "User" })
    public Response updateEnderecos(UsuarioEnderecoDTO dto) {
        try {
            UsuarioEnderecoResponseDTO response = service.updateEnderecos(this.getIdUsuario(), dto);
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PATCH
    @Path("/insert-endereco")
    @RolesAllowed({ "Admin", "User" })
    public Response insertEnderecos(EnderecoDTO dto) {
        try {
            EnderecoResponseDTO response = service.insertEndereco(this.getIdUsuario(), dto);
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @GET
    @Path("/contatos")
    @RolesAllowed({ "Admin", "User" })
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
    @RolesAllowed({ "Admin", "User" })
    public Response alterarContatos(UsuarioTelefoneDTO dto) {
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
    @RolesAllowed({"Admin","User"})
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response salvarImagem(@MultipartForm ImageForm form){
        String nomeImagem = "";

        try {
            nomeImagem = fileService.salvarImagemUsuario(form.getImagem(), form.getNomeImagem(), this.getIdUsuario());
        } catch (IOException e) {
            Result result = new Result(e.getMessage());
            return Response.status(Status.CONFLICT).entity(result).build();
        }

        // obtendo o login a partir do token
        String login = jwt.getSubject();
        UsuarioResponseDTO usuario = service.findByLogin(login);

        usuario = service.update(usuario.id(), nomeImagem);

        return Response.ok(usuario).build();

    }

    @GET
    @Path("/download/{nomeImagem}")
    @RolesAllowed({"Admin","User"})
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("nomeImagem") String nomeImagem) {
        Response.ResponseBuilder response = Response.ok(fileService.download(nomeImagem));
        response.header("Content-Disposition", "attachment;filename="+nomeImagem);
        return response.build();
    }

}
