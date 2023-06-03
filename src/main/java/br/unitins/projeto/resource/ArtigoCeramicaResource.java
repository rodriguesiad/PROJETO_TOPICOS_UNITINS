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
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import java.util.List;

@Path("/artigos-ceramica")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ArtigoCeramicaResource {

    @Inject
    ArtigoCeramicaService service;

    @Inject
    FileService fileService;

    private static final Logger LOG = Logger.getLogger(ArtigoCeramicaResource.class);

    @GET
    public List<ArtigoCeramicaResponseDTO> getAll() {
        LOG.info("Buscando todos os artigo de ceramicas.");
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public ArtigoCeramicaResponseDTO findById(@PathParam("id") Long id) {
        LOG.info("Buscando um artigo de ceramica pelo id.");
        return service.findById(id);
    }

    @POST
    @RolesAllowed({"Admin"})
    public Response insert(ArtigoCeramicaDTO dto) {
        LOG.infof("Inserindo um artigo de ceramica: %s", dto.descricao());
        Result result = null;

        try {
            ArtigoCeramicaResponseDTO response = service.create(dto);
            LOG.infof("Artigo de Ceramica (%d) criado com sucesso.", response.id());
            return Response.status(Status.CREATED).entity(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um artigo de ceramica.");
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
    public Response update(@PathParam("id") Long id, ArtigoCeramicaDTO dto) {
        LOG.infof("Alterando um artigo de ceramica: %s", dto.descricao());
        Result result = null;

        try {
            ArtigoCeramicaResponseDTO response = service.update(id, dto);
            LOG.infof("Artigo de Ceramica (%d) alterado com sucesso.", response.id());
            return Response.ok(response).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao alterar um artigo de ceramica.");
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
        LOG.infof("Deletando um artigo de ceramica: %s", id);
        Result result = null;

        try {
            service.delete(id);
            LOG.infof("Artigo de Ceramica (%d) deletado com sucesso.", id);
            return Response.status(Status.NO_CONTENT).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao deletar um artigo de ceramica.");
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
        LOG.info("Cotando o número de artigos de ceramica.");
        return service.count();
    }

    @GET
    @Path("/search/{nome}")
    public Response search(@PathParam("nome") String nome) {
        LOG.infof("Pesquisando artigos de ceramica pelo nome: %s", nome);
        Result result = null;

        try {
            List<ArtigoCeramicaResponseDTO> response = service.findByNome(nome);
            LOG.infof("Pesquisa realizada com sucesso.");
            return Response.status(Status.NO_CONTENT).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao pesquisar artigos de ceramica.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @PATCH
    @Path("/{id}/novaimagem")
    @RolesAllowed({"Admin"})
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response salvarImagem(@MultipartForm ImageForm form, @PathParam("id") Long id) {
        LOG.infof("Adicionando imagem a um artigo de ceramica: %s", id);
        Result result = null;

        try {
            ArtigoCeramicaResponseDTO produto = service.insertImagens(form, id);
            LOG.infof("Imagem adicionada com sucesso.");
            return Response.status(Status.NO_CONTENT).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao inserir a imagem.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
    }


    @GET
    @Path("/{id}/download/{nomeImagem}")
    @RolesAllowed({"Admin", "User"})
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("nomeImagem") String nomeImagem, @PathParam("id") Long id) {
        Response.ResponseBuilder response = Response.ok(fileService.download(nomeImagem, "produto", Long.toString(id)));
        response.header("Content-Disposition", "attachment;filename=" + nomeImagem);
        LOG.infof("Buscando imagem pelo nome.", nomeImagem);
        return response.build();
    }

}
