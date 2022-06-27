package br.com.willbigas.algafood.api.controller;

import br.com.willbigas.algafood.api.mapper.ProdutoMapper;
import br.com.willbigas.algafood.api.model.request.ProdutoRequestDTO;
import br.com.willbigas.algafood.api.model.response.ProdutoResponseDTO;
import br.com.willbigas.algafood.domain.model.Produto;
import br.com.willbigas.algafood.domain.service.ProdutoService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ProdutoResponseDTO salvar(@RequestBody ProdutoRequestDTO produtoRequestDTO) {
        Produto produto = produtoService.salvar(produtoMapper.toProduto(produtoRequestDTO));
        return produtoMapper.toResponseDTO(produto);
    }

    @GetMapping
    public List<ProdutoResponseDTO> listar() {
        return produtoMapper.toList(produtoService.findAll());
    }

    @GetMapping("/{id}")
    public ProdutoResponseDTO buscar(@PathVariable Long id) {
        return produtoMapper.toResponseDTO(produtoService.buscarOuFalhar(id));
    }
}
