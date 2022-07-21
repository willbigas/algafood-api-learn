package br.com.willbigas.algafood.domain.listener;


import br.com.willbigas.algafood.domain.event.PedidoCanceladoApplicationEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@Log4j2
public class EnviaSMSPedidoCanceladoListener {

    @EventListener
    public void aoCancelarPedido(PedidoCanceladoApplicationEvent event) {
        log.info(String.format("Enviando SMS do cancelamento do pedido %s as %s", event.getPedido().getCodigo(),
                event.getDataHoraEvento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));

    }
}
