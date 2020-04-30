package br.com.willbigas.algafood.di.notificacao;

import br.com.willbigas.algafood.annotation.TipoDoNotificador;
import br.com.willbigas.algafood.di.modelo.Cliente;
import br.com.willbigas.algafood.di.notificacao.enums.NivelUrgencia;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@TipoDoNotificador(NivelUrgencia.URGENTE)
@Component
public class NotificadorEmail implements Notificador {

    @Override
    public void notificar(Cliente cliente, String mensagem) {


        System.out.printf("Notificando %s através do e-mail %s : %s\n", cliente.getNome(), cliente.getEmail(), mensagem);
    }
}
