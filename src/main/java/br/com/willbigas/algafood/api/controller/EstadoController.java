package br.com.willbigas.algafood.api.controller;

import br.com.willbigas.algafood.domain.exception.EstadoNaoEncontradoException;
import br.com.willbigas.algafood.domain.model.Estado;
import br.com.willbigas.algafood.domain.repository.EstadoRepository;
import br.com.willbigas.algafood.domain.service.EstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private EstadoService estadoService;

    @GetMapping
    public List<Estado> listar() {
        return estadoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Estado buscar(@PathVariable Long id) {
        return estadoService.buscarOuFalhar(id);
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody @Valid Estado estado) {
        try {
            estado = estadoService.salvar(estado);
            return ResponseEntity.status(HttpStatus.CREATED).body(estado);
        } catch (EstadoNaoEncontradoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Estado  atualizar(@PathVariable Long id, @RequestBody @Valid Estado estado) {
        Estado estadoAtual = estadoService.buscarOuFalhar(id);
        BeanUtils.copyProperties(estado, estadoAtual, "id");
        return estadoService.salvar(estadoAtual);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        estadoService.excluir(id);
    }
}
