package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.domain.event.PedidoCanceladoApplicationEvent;
import br.com.willbigas.algafood.domain.model.Pedido;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;


@Service
public class FluxoPedidoService {

    private final PedidoService pedidoService;
    private final ApplicationEventPublisher publisher;

    public FluxoPedidoService(PedidoService pedidoService, ApplicationEventPublisher publisher) {
        this.pedidoService = pedidoService;
        this.publisher = publisher;
    }

    @Transactional
    public void confirmar(String codigoPedido) {
        Pedido pedido = pedidoService.buscarOuFalhar(codigoPedido);
        pedido.confirmar();
        pedidoService.salvar(pedido);
    }

    @Transactional
    public void entregar(String codigoPedido) {
        Pedido pedido = pedidoService.buscarOuFalhar(codigoPedido);
        pedido.entregar();
    }

    @Transactional
    public void cancelar(String codigoPedido) {
        Pedido pedido = pedidoService.buscarOuFalhar(codigoPedido);
        pedido.cancelar();
        publisher.publishEvent(new PedidoCanceladoApplicationEvent(this, pedido, OffsetDateTime.now()));
    }


}
