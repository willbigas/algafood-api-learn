package br.com.willbigas.algafood.api.controller;

import br.com.willbigas.algafood.domain.exception.CidadeNaoEncontradaException;
import br.com.willbigas.algafood.domain.exception.NegocioException;
import br.com.willbigas.algafood.domain.model.Cidade;
import br.com.willbigas.algafood.domain.service.CidadeService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Lista as cidades" , tags = {"Cidade"})
    public ResponseEntity<List<Cidade>> listar() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
//                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic()) // caches locais e compartilhados
//                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate()) // cache somente local
//                .cacheControl(CacheControl.noCache()) // Sempre armazena o cache em stale e sempre precisa ser revalidado.
//                .cacheControl(CacheControl.noStore()) // Não cacheavel
                .body(cidadeService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma cidade por ID" , tags = {"Cidade"})
    public Cidade buscar(@PathVariable Long id) {
        return cidadeService.buscarOuFalhar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra uma cidade" , tags = {"Cidade"})
    public Cidade adicionar(@RequestBody @Valid Cidade cidade) {
        try {
            return cidadeService.salvar(cidade);
        } catch (CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma cidade por ID" , tags = {"Cidade"})
    public Cidade atualizar(@PathVariable Long id, @RequestBody @Valid Cidade cidade) {
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
    @Operation(summary = "Exclui uma cidade por ID" , tags = {"Cidade"})
    public void remover(@PathVariable Long id) {
        cidadeService.excluir(id);
    }

}
