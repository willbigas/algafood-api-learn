package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.domain.model.Pedido;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Service
public class FluxoPedidoService {

    private final PedidoService pedidoService;

    public FluxoPedidoService(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Transactional
    public void confirmar(String codigoPedido) {
        Pedido pedido = pedidoService.buscarOuFalhar(codigoPedido);
        pedido.confirmar();
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
    }


}
