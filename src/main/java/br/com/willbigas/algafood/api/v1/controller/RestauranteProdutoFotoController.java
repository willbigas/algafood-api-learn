package br.com.willbigas.algafood.api.v1.controller;

import br.com.willbigas.algafood.api.v1.mapper.FotoProdutoMapper;
import br.com.willbigas.algafood.api.v1.model.request.FotoProdutoRequest;
import br.com.willbigas.algafood.api.v1.model.response.FotoProdutoResponseDTO;
import br.com.willbigas.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.willbigas.algafood.domain.model.FotoProduto;
import br.com.willbigas.algafood.domain.model.Produto;
import br.com.willbigas.algafood.domain.service.FotoProdutoService;
import br.com.willbigas.algafood.domain.service.interfaces.FotoStorageService.FotoRecuperada;
import br.com.willbigas.algafood.domain.service.RestauranteService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoResponseDTO buscar(@PathVariable Long idRestaurante, @PathVariable Long idProduto) {
        FotoProduto fotoProduto = fotoProdutoService.buscarOuFalhar(idRestaurante, idProduto);
        return fotoProdutoMapper.toResponseDTO(fotoProduto);
    }

    @GetMapping
    public ResponseEntity<?> buscarFoto(@PathVariable Long idRestaurante, @PathVariable Long idProduto, @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {

        try {
            FotoProduto fotoProduto = fotoProdutoService.buscarOuFalhar(idRestaurante, idProduto);

            MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
            List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);

            verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);

            FotoRecuperada fotoRecuperada = fotoProdutoService.recuperar(fotoProduto.getNomeArquivo());

            if (fotoRecuperada.temUrl()) {
                return ResponseEntity
                        .status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
                        .build();
            }

            if (fotoRecuperada.temInputStream()) {
                return ResponseEntity.ok()
                        .contentType(mediaTypeFoto)
                        .body(new InputStreamResource(fotoRecuperada.getInputStream()));
            }

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
        return null;
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

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long idRestaurante, @PathVariable Long idProduto) {
        fotoProdutoService.excluir(idRestaurante, idProduto);
    }

    private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {
        boolean compativel = mediaTypesAceitas.stream()
                .anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));

        if (!compativel) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
        }
    }


}
