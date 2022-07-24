package br.com.willbigas.algafood.api.controller;

import br.com.willbigas.algafood.api.exceptionhandler.Problem;
import br.com.willbigas.algafood.domain.exception.CidadeNaoEncontradaException;
import br.com.willbigas.algafood.domain.exception.NegocioException;
import br.com.willbigas.algafood.domain.model.Cidade;
import br.com.willbigas.algafood.domain.service.CidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/cidades")
@Tag(name = "Cidade", description = "Gerencia as cidades")
public class CidadeController {

    private final CidadeService cidadeService;

    public CidadeController(CidadeService cidadeService) {
        this.cidadeService = cidadeService;
    }

    @GetMapping
    @Operation(summary = "Lista as cidades", tags = {"Cidade"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, verifique o corpo ou path da requisição",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))}),
            @ApiResponse(responseCode = "500", description = "Erro inesperado no servidor"),})
    public ResponseEntity<List<Cidade>> listar() {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
//                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic()) // caches locais e compartilhados
//                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate()) // cache somente local
//                .cacheControl(CacheControl.noCache()) // Sempre armazena o cache em stale e sempre precisa ser revalidado.
//                .cacheControl(CacheControl.noStore()) // Não cacheavel
                .body(cidadeService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma cidade por ID", tags = {"Cidade"})
    public Cidade buscar(@Parameter(description = "ID de uma cidade", example = "123") @PathVariable Long id) {
        return cidadeService.buscarOuFalhar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra uma cidade", tags = {"Cidade"})
    public Cidade adicionar(@Parameter(name = "Corpo", description = "Representação de uma nova cidade") @RequestBody @Valid Cidade cidade) {
        try {
            return cidadeService.salvar(cidade);
        } catch (CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma cidade por ID", tags = {"Cidade"})
    public Cidade atualizar(@Parameter(description = "ID de uma cidade", example = "123") @PathVariable Long id, @Parameter(name = "Corpo", description = "Representação de uma nova cidade") @RequestBody @Valid Cidade cidade) {

        Cidade cidadeAtual = cidadeService.buscarOuFalhar(id);
        BeanUtils.copyProperties(cidade, cidadeAtual, "id");
        try {
            return cidadeService.salvar(cidadeAtual);
        } catch (CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui uma cidade por ID", tags = {"Cidade"})
    public void remover(@Parameter(description = "ID de uma cidade", example = "123") @PathVariable Long id) {
        cidadeService.excluir(id);
    }

}
