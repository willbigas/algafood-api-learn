package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.domain.model.Pedido;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {

    private final PedidoService pedidoService;

    public FluxoPedidoService(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Transactional
    public void confirmar(Long idPedido) {
        Pedido pedido = pedidoService.buscarOuFalhar(idPedido);
        pedido.confirmar();
    }

    @Transactional
    public void entregar(Long idPedido) {
        Pedido pedido = pedidoService.buscarOuFalhar(idPedido);
        pedido.entregar();
    }

    @Transactional
    public void cancelar(Long idPedido) {
        Pedido pedido = pedidoService.buscarOuFalhar(idPedido);
        pedido.cancelar();
    }
}
