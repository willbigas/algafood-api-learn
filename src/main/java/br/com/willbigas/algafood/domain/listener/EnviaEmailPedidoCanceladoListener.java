package br.com.willbigas.algafood.domain.listener;


import br.com.willbigas.algafood.domain.event.PedidoCanceladoApplicationEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.format.DateTimeFormatter;

@Component
@Log4j2
public class EnviaEmailPedidoCanceladoListener {

//    @EventListener // default -> Executa os eventos antes de persistir no banco
//    @TransactionalEventListener // Executa os eventos depois de persistir no banco (desamarrado)
//    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT) // Executa os eventos depois de dar flush no banco, porem amarrado a transação cercada pelo registro do evento

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void aoCancelarPedido(PedidoCanceladoApplicationEvent event) {
        log.info(String.format("Enviando E-mail do cancelamento do pedido %s as %s", event.getPedido().getCodigo(),
                event.getDataHoraEvento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
    }
}
