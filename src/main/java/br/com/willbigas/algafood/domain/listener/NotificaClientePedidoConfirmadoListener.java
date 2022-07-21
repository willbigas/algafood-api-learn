package br.com.willbigas.algafood.domain.listener;

import br.com.willbigas.algafood.domain.event.PedidoConfirmadoEvent;
import br.com.willbigas.algafood.domain.model.Mensagem;
import br.com.willbigas.algafood.domain.model.Pedido;
import br.com.willbigas.algafood.domain.service.interfaces.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class NotificaClientePedidoConfirmadoListener {

    private final EmailService emailService;

    public NotificaClientePedidoConfirmadoListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {

        Pedido pedido = event.getPedido();

        Mensagem mensagem = Mensagem.builder()
                .remetente("will.bigas@gmail.com")
                .destinatario("will.bigas@gmail.com")
                .assunto("AlgaFood - Pedido Confirmado")
                .variavel("pedido", pedido)
                .corpo("pedido-confirmado.html")
                .build();

        emailService.enviarEmail(mensagem, StandardCharsets.UTF_8);
    }
}
