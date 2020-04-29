package br.com.willbigas.algafood.di.notificacao;

import br.com.willbigas.algafood.di.modelo.Cliente;
import org.springframework.stereotype.Component;

@Component
public interface Notificador {

    void notificar(Cliente cliente, String mensagem);
}
