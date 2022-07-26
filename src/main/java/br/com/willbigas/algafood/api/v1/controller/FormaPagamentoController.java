package br.com.willbigas.algafood.api.v1.controller;

import br.com.willbigas.algafood.domain.exception.CidadeNaoEncontradaException;
import br.com.willbigas.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import br.com.willbigas.algafood.domain.exception.NegocioException;
import br.com.willbigas.algafood.domain.model.FormaPagamento;
import br.com.willbigas.algafood.domain.service.FormaPagamentoService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/formaPagamentos")
public class FormaPagamentoController {

    private final FormaPagamentoService formaPagamentoService;

    public FormaPagamentoController(FormaPagamentoService formaPagamentoService) {
        this.formaPagamentoService = formaPagamentoService;
    }

    @GetMapping
    public ResponseEntity<List<FormaPagamento>> listar(ServletWebRequest request) {

        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest()); // desabilita o shallow tags neste metodo
        String etag = buscaUltimaDataDeAtualizacao();

        if (request.checkNotModified(etag)) { // Verifica se o Etag recebido do request é igual ao etag provisionado anteriormente.
            return null;
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .eTag(etag)
                .body(formaPagamentoService.findAll());
    }

    @GetMapping("/{id}")
    public FormaPagamento buscar(@PathVariable Long id) {
        return formaPagamentoService.buscarOuFalhar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamento adicionar(@RequestBody @Valid FormaPagamento cidade) {
        try {
            return formaPagamentoService.salvar(cidade);
        } catch (FormaPagamentoNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public FormaPagamento atualizar(@PathVariable Long id, @RequestBody @Valid FormaPagamento formaPagamento) {

        try {
            FormaPagamento formaPagamentoAtual = formaPagamentoService.buscarOuFalhar(id);
            BeanUtils.copyProperties(formaPagamento, formaPagamentoAtual, "id");
            return formaPagamentoService.salvar(formaPagamentoAtual);
        } catch (CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        formaPagamentoService.excluir(id);
    }

    private String buscaUltimaDataDeAtualizacao() {
        String etag = "0";

        OffsetDateTime dataUltimaAtualizacao = formaPagamentoService.getUltimaDataAtualizacao();

        if (dataUltimaAtualizacao != null) {
            etag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        }
        return etag;
    }

}
