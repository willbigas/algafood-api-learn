package br.com.willbigas.algafood.api.controller;

import br.com.willbigas.algafood.domain.exception.GrupoNaoEncontradoException;
import br.com.willbigas.algafood.domain.exception.NegocioException;
import br.com.willbigas.algafood.domain.model.Grupo;
import br.com.willbigas.algafood.domain.service.GrupoService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/grupos")
public class GrupoController {

    private final GrupoService grupoService;

    public GrupoController(GrupoService grupoService) {
        this.grupoService = grupoService;
    }

    @GetMapping
    public List<Grupo> listar() {
        return grupoService.findAll();
    }

    @GetMapping("/{id}")
    public Grupo buscar(@PathVariable Long id) {
        return grupoService.buscarOuFalhar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Grupo adicionar(@RequestBody @Valid Grupo grupo) {
        try {
            return grupoService.salvar(grupo);
        } catch (GrupoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage() , e);
        }
    }

    @PutMapping("/{id}")
    public Grupo atualizar(@PathVariable Long id, @RequestBody @Valid Grupo grupo) {
        Grupo grupoAtual = grupoService.buscarOuFalhar(id);
        BeanUtils.copyProperties(grupo, grupoAtual, "id");
        try {
            return grupoService.salvar(grupoAtual);
        } catch (GrupoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage() , e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        grupoService.excluir(id);
    }

}
