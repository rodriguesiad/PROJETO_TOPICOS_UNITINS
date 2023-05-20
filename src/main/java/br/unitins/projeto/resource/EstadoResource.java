package br.unitins.projeto.resource;

import br.unitins.projeto.application.Result;
import br.unitins.projeto.dto.estado.EstadoDTO;
import br.unitins.projeto.dto.estado.EstadoResponseDTO;
import br.unitins.projeto.service.estado.EstadoService;

import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.List;

@Path("/estados")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EstadoResource {

    @Inject
    EstadoService service;

    @GET
    public List<EstadoResponseDTO> getAll() {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public EstadoResponseDTO findById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @POST
    public Response insert(EstadoDTO dto) {
        try {
            EstadoResponseDTO Estado = service.create(dto);
            return Response.status(Status.CREATED).entity(Estado).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, EstadoDTO dto) {
        try {
            EstadoResponseDTO Estado = service.update(id, dto);
            return Response.ok(Estado).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            service.delete(id);
            return Response.status(Status.NO_CONTENT).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }

    }

    @GET
    @Path("/count")
    public Long count() {
        return service.count();
    }

    @GET
    @Path("/search/{sigla}")
    public List<EstadoResponseDTO> search(@PathParam("sigla") String sigla) {
        return service.findBySigla(sigla);
    }

}

