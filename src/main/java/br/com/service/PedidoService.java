package br.com.service;

import br.com.collection.StatusPedido;
import br.com.domain.Pedido;
import br.com.exception.BusinessException;
import br.com.integration.ProductStockIntegration;
import br.com.repository.PedidoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.validation.Valid;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
public class PedidoService {

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    @RestClient
    ProductStockIntegration productStockIntegration;

    public List<Pedido> getAllPedido() {
        return pedidoRepository.listAll();
    }

    public Pedido getPedidoByid(@PathParam("id") Long id) throws BusinessException {

        Pedido pedido = pedidoRepository.findById(id);

        if (pedido == null) {
            throw new BusinessException("Não localizamos este pedido com o id " + id);
        } else {
            System.out.println("Pedido (" + pedido.getId() + ") foi consultado !");
            return pedido;
        }
    }

    public List<Pedido> findPedidosByStatus(StatusPedido statusPedido) {
        return pedidoRepository.getPedidosByStatus(statusPedido);
    }

    public Pedido createPedido(@Valid Pedido pedido) throws BusinessException {

        Response productStock = productStockIntegration.getProductById(pedido.getIdProduct());
        JsonObject stockInfo = productStock.readEntity(JsonObject.class);

        int qtdStock = stockInfo.getInt("qtdStock");
        if (productStock.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
            throw new BusinessException("Não localizamos um produto com o ID " + pedido.getIdProduct());
        } else if (qtdStock < 1) {
            throw new BusinessException("Não temos mais este produto no estoque, saldo igual a " + qtdStock);
        } else {
            pedidoRepository.persist(pedido);
            System.out.println("Pedido (" + pedido.getId() + ") enviado com o Status " + pedido.getStatusPedido() + " e Cupom nº " + pedido.getVoucher() + "");
            return pedido;
        }
    }

    public void aprovedPedido(@PathParam("idPedido") Long idPedido) throws BusinessException {

        Pedido pedido = pedidoRepository.findById(idPedido);

        if (pedido == null) {
            throw new BusinessException("Não localizamos nenhum pedido com este id " + idPedido);

        } else if (pedido.getStatusPedido() != StatusPedido.CREATE) {
            throw new BusinessException("Este produto já está com o Status: " + pedido.getStatusPedido());

        } else {
            pedido.setStatusPedido(StatusPedido.PAYMENT_APROVED);
            pedidoRepository.persist(pedido);
            System.out.println("Pedido (" + pedido.getId() + ") foi " + pedido.getStatusPedido());

        }
    }

    public void reprovedPedido(@PathParam("idPedido") Long idPedido) throws BusinessException {

        Pedido pedido = pedidoRepository.findById(idPedido);

        if (pedido == null) {
            throw new BusinessException("Não localizamos nenhum pedido com este id " + idPedido);

        } else if (pedido.getStatusPedido() != StatusPedido.CREATE) {
            throw new BusinessException("Este produto já está com o Status: " + pedido.getStatusPedido());

        } else {
            pedido.setStatusPedido(StatusPedido.PAYMENT_REPROVED);
            pedidoRepository.persist(pedido);

        }
    }

    public void canceledPedido(@PathParam("idPedido") Long idPedido) throws BusinessException {

        Pedido pedido = pedidoRepository.findById(idPedido);

        if (pedido == null) {
            throw new BusinessException("Não localizamos nenhum pedido com este id " + idPedido);
        } else {
            pedido.setStatusPedido(StatusPedido.CANCELED);
            pedidoRepository.persist(pedido);

        }
    }

}
