package br.com.willbigas.algafood.api.controller;

import br.com.willbigas.algafood.api.mapper.ProdutoMapper;
import br.com.willbigas.algafood.api.model.response.ProdutoResumidoResponseDTO;
import br.com.willbigas.algafood.domain.model.Restaurante;
import br.com.willbigas.algafood.domain.service.RestauranteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes/{idRestaurante}/produtos")
public class RestauranteProdutoController {

    private final RestauranteService restauranteService;
    private final ProdutoMapper produtoMapper;

    public RestauranteProdutoController(RestauranteService restauranteService, ProdutoMapper produtoMapper) {
        this.restauranteService = restauranteService;
        this.produtoMapper = produtoMapper;
    }

    @GetMapping
    public List<ProdutoResumidoResponseDTO> listar(@PathVariable Long idRestaurante) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(idRestaurante);
        return produtoMapper.toListProdutoResumido(restaurante.getProdutos());
    }

    @PutMapping("/{idProduto}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long idRestaurante , @PathVariable Long idProduto) {
        restauranteService.associarProduto(idRestaurante , idProduto);
    }

    @DeleteMapping("/{idProduto}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long idRestaurante , @PathVariable Long idProduto) {
        restauranteService.desassociarProduto(idRestaurante , idProduto);
    }
}
