package br.com.willbigas.algafood.api.controller;

import br.com.willbigas.algafood.api.mapper.FotoProdutoMapper;
import br.com.willbigas.algafood.api.model.request.FotoProdutoRequest;
import br.com.willbigas.algafood.api.model.response.FotoProdutoResponseDTO;
import br.com.willbigas.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.willbigas.algafood.domain.model.FotoProduto;
import br.com.willbigas.algafood.domain.model.Produto;
import br.com.willbigas.algafood.domain.service.FotoProdutoService;
import br.com.willbigas.algafood.domain.service.FotoStorageService;
import br.com.willbigas.algafood.domain.service.RestauranteService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes/{idRestaurante}/produtos/{idProduto}/foto")
public class RestauranteProdutoFotoController {

    private final FotoProdutoService fotoProdutoService;
    private final RestauranteService restauranteService;
    private final FotoProdutoMapper fotoProdutoMapper;
    private final FotoStorageService fotoStorageService;

    public RestauranteProdutoFotoController(FotoProdutoService fotoProdutoService, RestauranteService restauranteService, FotoProdutoMapper fotoProdutoMapper, FotoStorageService fotoStorageService) {
        this.fotoProdutoService = fotoProdutoService;
        this.restauranteService = restauranteService;
        this.fotoProdutoMapper = fotoProdutoMapper;
        this.fotoStorageService = fotoStorageService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoResponseDTO buscar(@PathVariable Long idRestaurante, @PathVariable Long idProduto) {
        FotoProduto fotoProduto = fotoProdutoService.buscarOuFalhar(idRestaurante, idProduto);
        return fotoProdutoMapper.toResponseDTO(fotoProduto);
    }

    @GetMapping
    public ResponseEntity<InputStreamResource> buscarFoto(@PathVariable Long idRestaurante, @PathVariable Long idProduto , @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {

        try {
            FotoProduto fotoProduto = fotoProdutoService.buscarOuFalhar(idRestaurante, idProduto);

            MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
            List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);

            verificarCompatibilidadeMediaType(mediaTypeFoto , mediaTypesAceitas);

            InputStream inputStream = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());

            return ResponseEntity.ok()
                    .contentType(mediaTypeFoto)
                    .body(new InputStreamResource(inputStream));

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoResponseDTO atualizarFoto(@PathVariable Long idRestaurante, @PathVariable Long idProduto, @Valid FotoProdutoRequest request) throws IOException {

        Produto produto = restauranteService.buscarOuFalhar(idRestaurante, idProduto);
        MultipartFile arquivo = request.getArquivo();
        String descricao = request.getDescricao();

        FotoProduto foto = new FotoProduto();
        foto.setProduto(produto);
        foto.setContentType(arquivo.getContentType());
        foto.setTamanho(arquivo.getSize());
        foto.setNomeArquivo(arquivo.getOriginalFilename());
        foto.setDescricao(descricao);
        foto = fotoProdutoService.salvar(foto, arquivo.getInputStream());

        return fotoProdutoMapper.toResponseDTO(foto);
    }

    private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {
        boolean compativel = mediaTypesAceitas.stream()
                .anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));

        if (!compativel) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
        }
    }


}
