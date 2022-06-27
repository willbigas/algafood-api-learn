package br.com.willbigas.algafood.api.controller;

import br.com.willbigas.algafood.api.mapper.FormaPagamentoMapper;
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
    private final FormaPagamentoMapper formaPagamentoMapper;

    public RestauranteFormaPagamentoController(RestauranteService restauranteService, FormaPagamentoMapper formaPagamentoMapper) {
        this.restauranteService = restauranteService;
        this.formaPagamentoMapper = formaPagamentoMapper;
    }

    @GetMapping
    public List<FormaPagamentoModel> listar(@PathVariable Long idRestaurante) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(idRestaurante);
        return formaPagamentoMapper.toList(restaurante.getFormasPagamento());
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
