package br.com.willbigas.algafood.di.notificacao;

import br.com.willbigas.algafood.annotation.TipoDoNotificador;
import br.com.willbigas.algafood.di.modelo.Cliente;
import br.com.willbigas.algafood.di.notificacao.enums.NivelUrgencia;
import org.springframework.stereotype.Component;

@TipoDoNotificador(NivelUrgencia.URGENTE)
@Component
public class NotificadorSMS implements Notificador {

    @Override
    public void notificar(Cliente cliente, String mensagem) {

        System.out.printf("Notificando %s por SMS através do telefone  %s : %s\n", cliente.getNome(), cliente.getTelefone(), mensagem);
    }
}
