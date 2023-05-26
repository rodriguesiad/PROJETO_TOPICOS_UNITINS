package br.unitins.projeto.resource;

import br.unitins.projeto.application.Result;
import br.unitins.projeto.dto.artigo_ceramica.ArtigoCeramicaDTO;
import br.unitins.projeto.dto.artigo_ceramica.ArtigoCeramicaResponseDTO;
import br.unitins.projeto.form.ImageForm;
import br.unitins.projeto.service.artigo_ceramica.ArtigoCeramicaService;
import br.unitins.projeto.service.file.FileService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import java.io.IOException;
import java.util.List;

@Path("/artigos-ceramica")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ArtigoCeramicaResource {

    @Inject
    ArtigoCeramicaService service;

    @Inject
    FileService fileService;

    @GET
    public List<ArtigoCeramicaResponseDTO> getAll() {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public ArtigoCeramicaResponseDTO findById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @POST
    @RolesAllowed({"Admin"})
    public Response insert(ArtigoCeramicaDTO dto) {
        try {
            ArtigoCeramicaResponseDTO ArtigoCeramica = service.create(dto);
            return Response.status(Status.CREATED).entity(ArtigoCeramica).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response update(@PathParam("id") Long id, ArtigoCeramicaDTO dto) {
        try {
            ArtigoCeramicaResponseDTO ArtigoCeramica = service.update(id, dto);
            return Response.ok(ArtigoCeramica).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    public Long count() {
        return service.count();
    }

    @GET
    @Path("/search/{nome}")
    public List<ArtigoCeramicaResponseDTO> search(@PathParam("nome") String nome) {
        return service.findByNome(nome);
    }

    @PATCH
    @Path("/{id}/novaimagem")
    @RolesAllowed({"Admin"})
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response salvarImagem(@MultipartForm ImageForm form, @PathParam("id") Long id) {
        try {
            ArtigoCeramicaResponseDTO produto = service.insertImagens(form, id);
            return Response.ok(produto).build();
        } catch (IOException e) {
            Result result = new Result(e.getMessage());
            return Response.status(Status.CONFLICT).entity(result).build();
        }
    }

    @GET
    @Path("/{id}/download/{nomeImagem}")
    @RolesAllowed({"Admin", "User"})
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("nomeImagem") String nomeImagem, @PathParam("id") Long id) {
        Response.ResponseBuilder response = Response.ok(fileService.download(nomeImagem, "produto", Long.toString(id)));
        response.header("Content-Disposition", "attachment;filename=" + nomeImagem);

        return response.build();
    }

}
