package br.com.willbigas.algafood.domain.event;

import br.com.willbigas.algafood.domain.model.Pedido;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.OffsetDateTime;


@Getter
public class PedidoCanceladoApplicationEvent  extends ApplicationEvent {

    private final OffsetDateTime dataHoraEvento;
    private final Pedido pedido;

    public PedidoCanceladoApplicationEvent(Object source, Pedido pedido , OffsetDateTime dataHoraEvento) {
        super(source);
        this.pedido = pedido;
        this.dataHoraEvento = dataHoraEvento;
    }

}
