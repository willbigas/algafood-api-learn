package br.com.willbigas.algafood.domain.listener;

import br.com.willbigas.algafood.domain.event.PedidoConfirmadoEvent;
import br.com.willbigas.algafood.domain.model.Mensagem;
import br.com.willbigas.algafood.domain.model.Pedido;
import br.com.willbigas.algafood.domain.service.interfaces.EmailService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.nio.charset.StandardCharsets;

@Component
public class NotificaClientePedidoConfirmadoListener {

    private final EmailService emailService;

    public NotificaClientePedidoConfirmadoListener(EmailService emailService) {
        this.emailService = emailService;
    }


    //    @EventListener // default -> Executa os eventos antes de persistir no banco
    //    @TransactionalEventListener // Executa os eventos depois de persistir no banco (desamarrado)
    //    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT) // Executa os eventos depois de dar flush no banco, porem amarrado a transação cercada pelo registro do evento

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
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
