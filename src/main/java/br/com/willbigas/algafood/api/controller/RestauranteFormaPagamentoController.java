package br.com.willbigas.algafood.api.controller;

import br.com.willbigas.algafood.api.converter.FormaPagamentoConverter;
import br.com.willbigas.algafood.api.model.FormaPagamentoModel;
import br.com.willbigas.algafood.domain.model.Restaurante;
import br.com.willbigas.algafood.domain.service.RestauranteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes/{idRestaurante}/formas-pagamento")
public class RestauranteFormaPagamentoController {

    private final RestauranteService restauranteService;
    private final FormaPagamentoConverter formaPagamentoConverter;

    public RestauranteFormaPagamentoController(RestauranteService restauranteService, FormaPagamentoConverter formaPagamentoConverter) {
        this.restauranteService = restauranteService;
        this.formaPagamentoConverter = formaPagamentoConverter;
    }

    @GetMapping
    public List<FormaPagamentoModel> listar(@PathVariable Long idRestaurante) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(idRestaurante);
        return formaPagamentoConverter.toList(restaurante.getFormasPagamento());
    }

    @PutMapping("/{idFormaPagamento}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long idRestaurante , @PathVariable Long idFormaPagamento) {
        restauranteService.associarFormaPagamento(idRestaurante , idFormaPagamento);
    }

    @DeleteMapping("/{idFormaPagamento}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long idRestaurante , @PathVariable Long idFormaPagamento) {
        restauranteService.desassociarFormaPagamento(idRestaurante , idFormaPagamento);
    }
}
