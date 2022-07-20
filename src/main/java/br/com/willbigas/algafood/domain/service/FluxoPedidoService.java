package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.domain.model.Mensagem;
import br.com.willbigas.algafood.domain.model.Pedido;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

@Service
public class FluxoPedidoService {

    private final PedidoService pedidoService;

    private final EmailService emailService;

    public FluxoPedidoService(PedidoService pedidoService, EmailService emailService) {
        this.pedidoService = pedidoService;
        this.emailService = emailService;
    }

    @Transactional
    public void confirmar(String codigoPedido) {
        Pedido pedido = pedidoService.buscarOuFalhar(codigoPedido);
        pedido.confirmar();


        Mensagem mensagem = Mensagem.builder()
                .remetente("will.bigas@gmail.com")
                .destinatario("will.bigas@gmail.com")
                .assunto("Pedido Confirmado -> " + pedido.getCodigo())
                .variavel("pedido" , pedido)
                .corpo("pedido-confirmado.html")
                .build();

        emailService.enviarEmail(mensagem, StandardCharsets.UTF_8);
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
