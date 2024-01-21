package br.com.resource;

import br.com.collection.StatusPedido;
import br.com.domain.Pedido;
import br.com.exception.BusinessException;
import br.com.service.PedidoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


@ApplicationScoped
@Path("api/v1/pedido")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {

    @Inject
    PedidoService pedidoService;

    @GET
    public List<Pedido> getAllPedido() {
        return pedidoService.getAllPedido();
    }

    @GET
    @Path("/{id}")
    public Pedido getPedidoById(@PathParam("id") Long id) throws BusinessException {
        Pedido pedido = pedidoService.getPedidoByid(id);
        return pedido;
    }

    @GET
    @Path("/status")
    public List<Pedido> getPedidoByStatus(@QueryParam("statusPedido") StatusPedido statusPedido){
        return pedidoService.findPedidosByStatus(statusPedido);
    }

    @POST
    @Transactional
    public Pedido createPedido(Pedido pedido) throws BusinessException {
        pedidoService.createPedido(pedido);
        return pedido;
    }

    @PUT
    @Path("/{idPedido}/aprovedPedido")
    @Transactional
    public Response aprovedPedido(@PathParam("idPedido") Long idPedido) throws BusinessException {
        pedidoService.aprovedPedido(idPedido);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("/{idPedido}/reprovedPedido")
    @Transactional
    public Response reprovedPedido(@PathParam("idPedido") Long idPedido) throws BusinessException {
        pedidoService.reprovedPedido(idPedido);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("/{idPedido}/canceledPedido")
    @Transactional
    public Response canceledPedido(@PathParam("idPedido") Long idPedido) throws BusinessException {
        pedidoService.canceledPedido(idPedido);
        return Response.status(Response.Status.OK).build();
    }

}
