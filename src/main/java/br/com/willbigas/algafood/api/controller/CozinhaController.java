package br.com.willbigas.algafood.api.controller;

import br.com.willbigas.algafood.api.model.CozinhasXmlWrapper;
import br.com.willbigas.algafood.domain.model.Cozinha;
import br.com.willbigas.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @GetMapping
    public List<Cozinha> listar() {
        return cozinhaRepository.listar();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhasXmlWrapper listarXml() {
        return new CozinhasXmlWrapper(cozinhaRepository.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long id) {
        Cozinha cozinha = cozinhaRepository.buscar(id);

        if (cozinha != null) return ResponseEntity.ok(cozinha);

        return ResponseEntity.notFound().build();

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha) {
        return cozinhaRepository.salvar(cozinha);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
        Cozinha cozinhaBuscada = cozinhaRepository.buscar(id);

        if (cozinhaBuscada != null) {
            BeanUtils.copyProperties(cozinha, cozinhaBuscada, "id"); // faz a copia das entidades
            cozinhaRepository.salvar(cozinhaBuscada);
            return ResponseEntity.ok(cozinhaBuscada);
        }

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cozinha> remover(@PathVariable Long id) {

        try {

            Cozinha cozinha = cozinhaRepository.buscar(id);
            if (cozinha != null) {
                cozinhaRepository.remover(cozinha);
                return ResponseEntity.noContent().build();
            }

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }


        return ResponseEntity.notFound().build();

    }
}
