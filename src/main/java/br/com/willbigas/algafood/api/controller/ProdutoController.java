package br.com.willbigas.algafood.api.controller;

import br.com.willbigas.algafood.api.mapper.ProdutoMapper;
import br.com.willbigas.algafood.api.model.response.ProdutoResponseDTO;
import br.com.willbigas.algafood.domain.service.ProdutoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ProdutoMapper produtoMapper;

    public ProdutoController(ProdutoService produtoService, ProdutoMapper produtoMapper) {
        this.produtoService = produtoService;
        this.produtoMapper = produtoMapper;
    }

    @GetMapping
    public List<ProdutoResponseDTO> listar() {
        return produtoMapper.toList(produtoService.findAll());
    }

    @GetMapping("/{id}")
    public ProdutoResponseDTO buscar(@PathVariable Long id) {
        return produtoMapper.toModel(produtoService.buscarOuFalhar(id));
    }
}
