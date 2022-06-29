package br.com.willbigas.algafood.api.controller;

import br.com.willbigas.algafood.domain.exception.CidadeNaoEncontradaException;
import br.com.willbigas.algafood.domain.exception.NegocioException;
import br.com.willbigas.algafood.domain.model.Cidade;
import br.com.willbigas.algafood.domain.service.CidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

    private final CidadeService cidadeService;

    public CidadeController(CidadeService cidadeService) {
        this.cidadeService = cidadeService;
    }

    @GetMapping
    public List<Cidade> listar() {
        return cidadeService.findAll();
    }

    @GetMapping("/{id}")
    public Cidade buscar(@PathVariable Long id) {
        return cidadeService.buscarOuFalhar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade adicionar(@RequestBody @Valid Cidade cidade) {
        try {
            return cidadeService.salvar(cidade);
        } catch (CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage() , e);
        }
    }

    @PutMapping("/{id}")
    public Cidade atualizar(@PathVariable Long id, @RequestBody @Valid Cidade cidade) {
        Cidade cidadeAtual = cidadeService.buscarOuFalhar(id);
        BeanUtils.copyProperties(cidade, cidadeAtual, "id");
        try {
            return cidadeService.salvar(cidadeAtual);
        } catch (CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage() , e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cidadeService.excluir(id);
    }

}
