package br.com.willbigas.algafood.api.controller;

import br.com.willbigas.algafood.api.mapper.FotoProdutoMapper;
import br.com.willbigas.algafood.api.model.request.FotoProdutoRequest;
import br.com.willbigas.algafood.api.model.response.FotoProdutoResponseDTO;
import br.com.willbigas.algafood.domain.model.FotoProduto;
import br.com.willbigas.algafood.domain.model.Produto;
import br.com.willbigas.algafood.domain.service.FotoProdutoService;
import br.com.willbigas.algafood.domain.service.RestauranteService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/restaurantes/{idRestaurante}/produtos/{idProduto}/foto")
public class RestauranteProdutoFotoController {

    private final FotoProdutoService fotoProdutoService;
    private final RestauranteService restauranteService;
    private final FotoProdutoMapper fotoProdutoMapper;

    public RestauranteProdutoFotoController(FotoProdutoService fotoProdutoService, RestauranteService restauranteService, FotoProdutoMapper fotoProdutoMapper) {
        this.fotoProdutoService = fotoProdutoService;
        this.restauranteService = restauranteService;
        this.fotoProdutoMapper = fotoProdutoMapper;
    }


    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoResponseDTO atualizarFoto(@PathVariable Long idRestaurante, @PathVariable Long idProduto, @Valid FotoProdutoRequest request) {

        Produto produto = restauranteService.buscarOuFalhar(idRestaurante, idProduto);
        MultipartFile arquivo = request.getArquivo();
        String descricao = request.getDescricao();

        FotoProduto foto = new FotoProduto();
        foto.setProduto(produto);
        foto.setContentType(arquivo.getContentType());
        foto.setTamanho(arquivo.getSize());
        foto.setNomeArquivo(arquivo.getOriginalFilename());
        foto.setDescricao(descricao);
        foto = fotoProdutoService.salvar(foto);

        return fotoProdutoMapper.toResponseDTO(foto);
    }
}
