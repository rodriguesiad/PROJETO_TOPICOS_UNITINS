package br.unitins.projeto.resource;

import br.unitins.projeto.application.Result;
import br.unitins.projeto.dto.estado.EstadoDTO;
import br.unitins.projeto.dto.estado.EstadoResponseDTO;
import br.unitins.projeto.service.estado.EstadoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
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
    @RolesAllowed({"Admin"})
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
    @RolesAllowed({"Admin"})
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
    @RolesAllowed({"Admin"})
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

