package br.com.willbigas.algafood.api.controller;

import br.com.willbigas.algafood.domain.service.FluxoPedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/pedidos/{idPedido}")
public class FluxoPedidoController {

    private final FluxoPedidoService fluxoPedidoService;

    public FluxoPedidoController(FluxoPedidoService fluxoPedidoService) {
        this.fluxoPedidoService = fluxoPedidoService;
    }

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable Long idPedido) {
        fluxoPedidoService.confirmar(idPedido);
    }

}
