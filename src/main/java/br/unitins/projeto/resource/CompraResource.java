package br.unitins.projeto.resource;

import br.unitins.projeto.application.Result;
import br.unitins.projeto.dto.compra.CompraDTO;
import br.unitins.projeto.dto.compra.CompraResponseDTO;
import br.unitins.projeto.dto.compra.StatusCompraDTO;
import br.unitins.projeto.dto.historico_entrega.HistoricoEntregaDTO;
import br.unitins.projeto.dto.historico_entrega.HistoricoEntregaResponseDTO;
import br.unitins.projeto.dto.usuario.UsuarioResponseDTO;
import br.unitins.projeto.service.compra.CompraService;
import br.unitins.projeto.service.usuario.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

@Path("/compra")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CompraResource {

    @Inject
    UsuarioService usuarioService;

    @Inject
    CompraService service;

    @Inject
    JsonWebToken jwt;

    private Long getIdUsuario() {
        String login = jwt.getSubject();
        UsuarioResponseDTO usuario = usuarioService.findByLogin(login);
        return usuario.id();
    }

    @POST
    @RolesAllowed({"Admin", "User"})
    public Response insert(@Valid CompraDTO dto) {
        try {
            CompraResponseDTO compra = service.create(dto, getIdUsuario());
            return Response.status(Status.CREATED).entity(compra).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PATCH
    @RolesAllowed({"Admin", "User"})
    @Path("/{idCompra}/alterar-status")
    public Response alterStatus(@PathParam("idCompra") Long id, @Valid StatusCompraDTO dto) {
        try {
            CompraResponseDTO response = service.alterStatusCompra(id, dto);
            return Response.ok().entity(response).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @GET
    @RolesAllowed({"Admin", "User"})
    @Path("/{idCompra}/historico-entrega")
    public Response getHistoricoEntrega(@PathParam("idCompra") Long id) {
        try {
            List<HistoricoEntregaResponseDTO> response = service.getHistoricoEntrega(id);
            return Response.ok().entity(response).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PATCH
    @RolesAllowed({"Admin", "User"})
    @Path("/{idCompra}/historico-entrega")
    public Response insertHistoricoEntrega(@PathParam("idCompra") Long id, @Valid HistoricoEntregaDTO dto) {
        try {
            HistoricoEntregaResponseDTO response = service.insertHistoricoEntrega(id, dto);
            return Response.ok().entity(response).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

}