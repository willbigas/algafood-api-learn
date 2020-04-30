package br.com.willbigas.algafood.di.notificacao;

import br.com.willbigas.algafood.annotation.TipoDoNotificador;
import br.com.willbigas.algafood.di.modelo.Cliente;
import br.com.willbigas.algafood.di.notificacao.enums.NivelUrgencia;
import org.springframework.stereotype.Component;

@TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
@Component
public class NotificadorEmailMock implements Notificador {

    public NotificadorEmailMock() {
        System.out.println("NotificadorEmail MOCK");
    }

    @Override
    public void notificar(Cliente cliente, String mensagem) {
        System.out.printf("MOCK: Notificação seria enviada para %s através do e-mail %s : %s\n", cliente.getNome(), cliente.getEmail(), mensagem);
    }
}
