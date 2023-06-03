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
import org.jboss.logging.Logger;

import java.util.List;

@Path("/artesoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ArtesaoResource {

    @Inject
    ArtesaoService service;

    private static final Logger LOG = Logger.getLogger(ArtesaoResource.class);

    @GET
    public List<ArtesaoResponseDTO> getAll() {
        LOG.info("Buscando todos os artesões.");
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public ArtesaoResponseDTO findById(@PathParam("id") Long id) {
        LOG.info("Buscando um artesão pelo id.");
        return service.findById(id);
    }

    @POST
    @RolesAllowed({"Admin"})
    public Response insert(@Valid ArtesaoDTO dto) {
        LOG.infof("Inserindo um artesão: %s", dto.descricao());
        Result result = null;

        try {
            ArtesaoResponseDTO response = service.create(dto);
            LOG.infof("Artesão (%d) criado com sucesso.", response.id());
            return Response.status(Status.CREATED).entity(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um artesão.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response update(@PathParam("id") Long id, @Valid ArtesaoDTO dto) {
        LOG.infof("Alterando um artesão: %s", dto.descricao());
        Result result = null;

        try {
            ArtesaoResponseDTO response = service.update(id, dto);
            LOG.infof("Artesão (%d) alterado com sucesso.", response.id());
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao alterar um artesão.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long id) {
        LOG.infof("Deletando um artesão: %s", id);
        Result result = null;

        try {
            service.delete(id);
            LOG.infof("Artesão (%d) deletado com sucesso.", id);
            return Response.status(Status.NO_CONTENT).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao deletar um artesão.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @GET
    @Path("/search/{nome}")
    public Response search(@PathParam("nome") String nome) {
        LOG.infof("Pesquisando artesões pelo nome: %s", nome);
        Result result = null;

        try {
            List<ArtesaoResponseDTO> response = service.findByNome(nome);
            LOG.infof("Pesquisa realizada com sucesso.");
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao pesquisar artesões.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

}

