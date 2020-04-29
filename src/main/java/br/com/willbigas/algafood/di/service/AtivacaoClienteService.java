package br.com.willbigas.algafood.di.service;

import br.com.willbigas.algafood.di.modelo.Cliente;
import br.com.willbigas.algafood.di.notificacao.Notificador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AtivacaoClienteService {

    @Autowired
    private Notificador notificador;

//    @Autowired
//    public AtivacaoClienteService(Notificador notificador) {
//        this.notificador = notificador;
//
//        System.out.println("AtivacaoClienteService: " + notificador);
//    }

//    public AtivacaoClienteService(String algoqualquer) {
//
//    }

    public void ativar(Cliente cliente) {
        cliente.ativar();
        notificador.notificar(cliente, "Seu Cadastro no sistema está ativo!");
    }


//    @Autowired
//    public void setNotificador(Notificador notificador) {
//        this.notificador = notificador;
//    }
}
