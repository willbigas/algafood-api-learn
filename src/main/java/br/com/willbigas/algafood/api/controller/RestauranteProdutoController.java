package br.com.willbigas.algafood.api.controller;

import br.com.willbigas.algafood.api.mapper.ProdutoMapper;
import br.com.willbigas.algafood.api.model.request.ProdutoRequestSimplificadoDTO;
import br.com.willbigas.algafood.api.model.response.ProdutoResponseDTO;
import br.com.willbigas.algafood.api.model.response.ProdutoResumidoResponseDTO;
import br.com.willbigas.algafood.domain.model.Produto;
import br.com.willbigas.algafood.domain.model.Restaurante;
import br.com.willbigas.algafood.domain.service.ProdutoService;
import br.com.willbigas.algafood.domain.service.RestauranteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes/{idRestaurante}/produtos")
public class RestauranteProdutoController {

    private final RestauranteService restauranteService;
    private final ProdutoService produtoService;
    private final ProdutoMapper produtoMapper;

    public RestauranteProdutoController(RestauranteService restauranteService, ProdutoService produtoService, ProdutoMapper produtoMapper) {
        this.restauranteService = restauranteService;
        this.produtoService = produtoService;
        this.produtoMapper = produtoMapper;
    }

    @GetMapping
    public List<ProdutoResumidoResponseDTO> listar(@PathVariable Long idRestaurante , @RequestParam(required = false) boolean incluirInativos) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(idRestaurante);

        if (incluirInativos) {
            return produtoMapper.toListProdutoResumido(produtoService.findByRestaurante(restaurante));
        }

        return produtoMapper.toListProdutoResumido(produtoService.findByRestauranteAndAtivoIsTrue(restaurante));
    }


    @PostMapping
    public ProdutoResponseDTO salvar(@PathVariable Long idRestaurante, @RequestBody ProdutoRequestSimplificadoDTO produtoRequestDTO) {

        Restaurante restaurante = restauranteService.buscarOuFalhar(idRestaurante);
        Produto produto = produtoMapper.toProduto(produtoRequestDTO);

        produto = produtoService.adicionarRestaurante(produto , restaurante);
        return produtoMapper.toResponseDTO(produto);
    }

    @GetMapping("/{idProduto}")
    public ProdutoResumidoResponseDTO buscarProduto(@PathVariable Long idRestaurante, @PathVariable Long idProduto) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(idRestaurante);
        return produtoMapper.toProdutoResumido(restauranteService.buscarOuFalhar(restaurante.getId(), idProduto));
    }


    @PutMapping("/{idProduto}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long idRestaurante, @PathVariable Long idProduto) {
        restauranteService.associarProduto(idRestaurante, idProduto);
    }

    @DeleteMapping("/{idProduto}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long idRestaurante, @PathVariable Long idProduto) {
        restauranteService.desassociarProduto(idRestaurante, idProduto);
    }


}
