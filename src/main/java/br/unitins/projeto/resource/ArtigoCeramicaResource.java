package br.unitins.projeto.resource;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import br.unitins.projeto.application.Result;
import br.unitins.projeto.dto.artigo_ceramica.ArtigoCeramicaDTO;
import br.unitins.projeto.dto.artigo_ceramica.ArtigoCeramicaResponseDTO;
import br.unitins.projeto.service.artigo_ceramica.ArtigoCeramicaService;

@Path("/artigos-ceramica")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ArtigoCeramicaResource {

    @Inject
    ArtigoCeramicaService service;

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

}
