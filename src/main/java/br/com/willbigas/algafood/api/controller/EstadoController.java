package br.com.willbigas.algafood.api.controller;

import br.com.willbigas.algafood.domain.exception.EntidadeEmUsoException;
import br.com.willbigas.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.willbigas.algafood.domain.model.Estado;
import br.com.willbigas.algafood.domain.repository.EstadoRepository;
import br.com.willbigas.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @GetMapping
    public List<Estado> listar() {
        return estadoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estado> buscar(@PathVariable Long id) {
        Optional<Estado> estado = estadoRepository.findById(id);

        if (estado.isPresent()) return ResponseEntity.ok(estado.get());

        return ResponseEntity.notFound().build();

    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Estado estado) {
        try {
            estado = cadastroEstadoService.salvar(estado);
            return ResponseEntity.status(HttpStatus.CREATED).body(estado);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Estado estado) {
        Optional<Estado> estadoBuscado = estadoRepository.findById(id);
        if (estadoBuscado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            BeanUtils.copyProperties(estado, estadoBuscado.get(), "id"); // faz a copia das entidades
            cadastroEstadoService.salvar(estadoBuscado.get());
            return ResponseEntity.ok(estadoBuscado);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Estado> remover(@PathVariable Long id) {

        try {
            cadastroEstadoService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
