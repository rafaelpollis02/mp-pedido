package br.com.repository;

import br.com.collection.StatusPedido;
import br.com.domain.Pedido;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {

    public List<Pedido> getPedidosByStatus(StatusPedido statusPedido) {
        return find("statusPedido", statusPedido).list();
    }
}
