package br.unitins.projeto.resource;

import br.unitins.projeto.application.Result;
import br.unitins.projeto.dto.artesao.ArtesaoDTO;
import br.unitins.projeto.dto.artesao.ArtesaoResponseDTO;
import br.unitins.projeto.service.artesao.ArtesaoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
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

@Path("/artesoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ArtesaoResource {

    @Inject
    ArtesaoService service;

    @GET
    public List<ArtesaoResponseDTO> getAll() {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public ArtesaoResponseDTO findById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @POST
    @RolesAllowed({"Admin"})
    public Response insert(@Valid ArtesaoDTO dto) {
        try {
            ArtesaoResponseDTO Artesao = service.create(dto);
            return Response.status(Status.CREATED).entity(Artesao).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response update(@PathParam("id") Long id, @Valid ArtesaoDTO dto) {
        try {
            ArtesaoResponseDTO Artesao = service.update(id, dto);
            return Response.ok(Artesao).build();
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
    @RolesAllowed({"Admin"})
    public Long count() {
        return service.count();
    }

    @GET
    @Path("/search/{nome}")
    public List<ArtesaoResponseDTO> search(@PathParam("nome") String nome) {
        return service.findByNome(nome);
    }

}

