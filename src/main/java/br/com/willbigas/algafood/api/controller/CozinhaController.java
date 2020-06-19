package br.com.willbigas.algafood.api.controller;

import br.com.willbigas.algafood.domain.exception.EntidadeEmUsoException;
import br.com.willbigas.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.willbigas.algafood.domain.model.Cozinha;
import br.com.willbigas.algafood.domain.repository.CozinhaRepository;
import br.com.willbigas.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @GetMapping
    public List<Cozinha> listar() {
        return cozinhaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long id) {
        Optional<Cozinha> cozinha = cozinhaRepository.findById(id);

        if (cozinha.isPresent()) return ResponseEntity.ok(cozinha.get());

        return ResponseEntity.notFound().build();

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha) {
        return cadastroCozinhaService.salvar(cozinha);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
        Optional<Cozinha> cozinhaBuscada = cozinhaRepository.findById(id);

        if (cozinhaBuscada.isPresent()) {
            BeanUtils.copyProperties(cozinha, cozinhaBuscada.get(), "id"); // faz a copia das entidades
            cadastroCozinhaService.salvar(cozinhaBuscada.get());
            return ResponseEntity.ok(cozinhaBuscada.get());
        }

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cozinha> remover(@PathVariable Long id) {

        try {
            cadastroCozinhaService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
