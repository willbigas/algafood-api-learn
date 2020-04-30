package br.com.willbigas.algafood.listener;

import br.com.willbigas.algafood.event.ClienteAtivadoEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EmissaoNotaFiscalListener {

    @EventListener
    public void clienteAtivadoListener(ClienteAtivadoEvent event) {
        System.out.println("Emitindo nota fiscal para cliente" + event.getCliente().getNome());
    }

}
