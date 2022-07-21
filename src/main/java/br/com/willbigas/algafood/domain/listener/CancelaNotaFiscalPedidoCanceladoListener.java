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
public class CancelaNotaFiscalPedidoCanceladoListener {

//    @EventListener // default -> Executa os eventos antes de persistir no banco
//    @TransactionalEventListener // Executa os eventos depois de persistir no banco (desamarrado)
//    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT) // Executa os eventos depois de dar flush no banco, porem amarrado a transação cercada pelo registro do evento

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void aoCancelarPedido(PedidoCanceladoApplicationEvent event) {
//        if (true) throw new IllegalArgumentException("Erro ao cancelar NF"); // simulando um problema em um processador de eventos

        log.info(String.format("Cancela a NF do pedido devido ao cancelamento do pedido %s as %s", event.getPedido().getCodigo(),
                event.getDataHoraEvento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
    }
}
