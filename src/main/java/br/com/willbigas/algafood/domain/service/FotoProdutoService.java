package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.domain.model.FotoProduto;
import br.com.willbigas.algafood.domain.repository.FotoProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FotoProdutoService {

    private final FotoProdutoRepository fotoProdutoRepository;

    public FotoProdutoService(FotoProdutoRepository fotoProdutoRepository) {
        this.fotoProdutoRepository = fotoProdutoRepository;
    }

    @Transactional
    public FotoProduto salvar(FotoProduto foto) {
        return fotoProdutoRepository.save(foto);
    }
}
