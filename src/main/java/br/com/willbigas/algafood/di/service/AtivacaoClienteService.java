package br.com.willbigas.algafood.di.service;

import br.com.willbigas.algafood.annotation.TipoDoNotificador;
import br.com.willbigas.algafood.di.modelo.Cliente;
import br.com.willbigas.algafood.di.notificacao.Notificador;
import br.com.willbigas.algafood.di.notificacao.enums.NivelUrgencia;
import br.com.willbigas.algafood.event.ClienteAtivadoEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AtivacaoClienteService {

    @TipoDoNotificador(NivelUrgencia.URGENTE)
    @Autowired
    private Notificador notificador;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

//    @Autowired
//    public AtivacaoClienteService(Notificador notificador) {
//        this.notificador = notificador;
//
//        System.out.println("AtivacaoClienteService: " + notificador);
//    }

//    public AtivacaoClienteService(String algoqualquer) {
//
//    }

    //    @PostConstruct
    public void init() {
        System.out.println("INIT" + notificador);
    }

    //    @PreDestroy
    public void destroy() {
        System.out.println("DESTROY");
    }

    public void ativar(Cliente cliente) {
        cliente.ativar();
        eventPublisher.publishEvent(new ClienteAtivadoEvent(cliente));
    }


//    @Autowired
//    public void setNotificador(Notificador notificador) {
//        this.notificador = notificador;
//    }
}
