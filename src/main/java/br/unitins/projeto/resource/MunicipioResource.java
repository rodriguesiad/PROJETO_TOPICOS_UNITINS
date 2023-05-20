package br.unitins.projeto.resource;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import br.unitins.projeto.application.Result;
import br.unitins.projeto.dto.municipio.MunicipioDTO;
import br.unitins.projeto.dto.municipio.MunicipioResponseDTO;
import br.unitins.projeto.service.municipio.MunicipioService;

@Path("/municipios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MunicipioResource {
    
    @Inject
    MunicipioService service;

    @GET
    public List<MunicipioResponseDTO> getAll() {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public MunicipioResponseDTO findById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @POST
    public Response insert(MunicipioDTO dto) {
        try {
            MunicipioResponseDTO Municipio = service.create(dto);
            return Response.status(Status.CREATED).entity(Municipio).build();
        } catch(ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, MunicipioDTO dto) {
        try {
            MunicipioResponseDTO Municipio = service.update(id, dto);
            return Response.ok(Municipio).build();
        } catch(ConstraintViolationException e) {
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
    public Long count(){
        return service.count();
    }

    @GET
    @Path("/search/{descricao}")
    public List<MunicipioResponseDTO> search(@PathParam("descricao") String descricao){
        return service.findByDescricao(descricao);
    }

}

