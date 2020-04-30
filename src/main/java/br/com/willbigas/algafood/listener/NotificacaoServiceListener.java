package br.com.willbigas.algafood.listener;

import br.com.willbigas.algafood.annotation.TipoDoNotificador;
import br.com.willbigas.algafood.di.notificacao.Notificador;
import br.com.willbigas.algafood.di.notificacao.NotificadorProperties;
import br.com.willbigas.algafood.di.notificacao.enums.NivelUrgencia;
import br.com.willbigas.algafood.event.ClienteAtivadoEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoServiceListener {

    @TipoDoNotificador(NivelUrgencia.URGENTE)
    @Autowired
    private Notificador notificador;

    @Autowired
    private NotificadorProperties properties;


    @EventListener
    public void clienteAtivadoListener(ClienteAtivadoEvent event) {
        System.out.println("Host: " + properties.getHostServidor());
        System.out.println("Porta: " + properties.getPortaServidor());

        notificador.notificar(event.getCliente() , "Seu cadastro no sistema está ativo!");
    }
}
