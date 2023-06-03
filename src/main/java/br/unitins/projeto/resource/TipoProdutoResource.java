package br.unitins.projeto.resource;

import br.unitins.projeto.application.Result;
import br.unitins.projeto.dto.tipo_produto.TipoProdutoDTO;
import br.unitins.projeto.dto.tipo_produto.TipoProdutoResponseDTO;
import br.unitins.projeto.service.tipo_produto.TipoProdutoService;
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

@Path("/tipos-produtos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TipoProdutoResource {

    @Inject
    TipoProdutoService service;

    private static final Logger LOG = Logger.getLogger(TipoProdutoResource.class);

    @GET
    public List<TipoProdutoResponseDTO> getAll() {
        LOG.info("Buscando todos os tipos produto.");
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public TipoProdutoResponseDTO findById(@PathParam("id") Long id) {
        LOG.info("Buscando um tipos produto pelo id.");
        return service.findById(id);
    }

    @POST
    @RolesAllowed({"Admin"})
    public Response insert(@Valid TipoProdutoDTO dto) {
        LOG.infof("Inserindo um tipo produto: %s", dto.descricao());
        Result result = null;

        try {
            TipoProdutoResponseDTO response = service.create(dto);
            LOG.infof("Tipo Produto (%d) criado com sucesso.", response.id());
            return Response.status(Status.CREATED).entity(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um tipo produto.");
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
    public Response update(@PathParam("id") Long id, @Valid TipoProdutoDTO dto) {
        LOG.infof("Alterando um tipo produto: %s", dto.descricao());
        Result result = null;

        try {
            TipoProdutoResponseDTO response = service.update(id, dto);
            LOG.infof("Tipo Produto (%d) alterado com sucesso.", response.id());
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao alterar um tipo produto.");
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
        LOG.infof("Deletando um tipo produto: %s", id);
        Result result = null;

        try {
            service.delete(id);
            LOG.infof("Tipo Produto (%d) deletado com sucesso.", id);
            return Response.status(Status.NO_CONTENT).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao deletar um tipo produto.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @GET
    @Path("/count")
    public Long count() {
        LOG.info("Cotando o número de tipos de produto.");
        return service.count();
    }

    @GET
    @Path("/search/{descricao}")
    public Response search(@PathParam("descricao") String descricao) {
        LOG.infof("Pesquisando tipos produto pelo nome: %s", descricao);
        Result result = null;

        try {
            List<TipoProdutoResponseDTO> response = service.findByDescricao(descricao);
            LOG.infof("Pesquisa realizada com sucesso.");
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao pesquisar tipos produto.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

}
