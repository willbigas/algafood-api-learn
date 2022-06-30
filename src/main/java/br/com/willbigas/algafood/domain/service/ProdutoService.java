package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.domain.exception.EntidadeEmUsoException;
import br.com.willbigas.algafood.domain.exception.ProdutoNaoEncontradoException;
import br.com.willbigas.algafood.domain.model.Produto;
import br.com.willbigas.algafood.domain.model.Restaurante;
import br.com.willbigas.algafood.domain.repository.ProdutoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    private static final String MSG_PRODUTO_EM_USO
            = "Produto de código %d não pode ser removido, pois está em uso";

    private static final String MSG_PRODUTO_NAO_ENCONTRADO
            = "Não existe um cadastro de produto com o código %d";

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    @Transactional
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    @Transactional
    public void excluir(Long idProduto) {
        try {
            produtoRepository.deleteById(idProduto);
            produtoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new ProdutoNaoEncontradoException(String.format(MSG_PRODUTO_NAO_ENCONTRADO, idProduto));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_PRODUTO_EM_USO, idProduto));
        }
    }

    @Transactional
    public Produto adicionarRestaurante(Produto produto , Restaurante restaurante) {
        produto.setRestaurante(restaurante);
        produto = salvar(produto);
        return produto;
    }

    public Produto buscarOuFalhar(Long idProduto) {
        return produtoRepository.findById(idProduto)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(
                        String.format(MSG_PRODUTO_NAO_ENCONTRADO, idProduto)));
    }

}
