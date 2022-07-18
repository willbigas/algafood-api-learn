package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.domain.model.FotoProduto;
import br.com.willbigas.algafood.domain.repository.FotoProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FotoProdutoService {

    private final FotoProdutoRepository fotoProdutoRepository;

    public FotoProdutoService(FotoProdutoRepository fotoProdutoRepository) {
        this.fotoProdutoRepository = fotoProdutoRepository;
    }

    @Transactional
    public FotoProduto salvar(FotoProduto foto) {
        Long idRestaurante = foto.getProduto().getRestaurante().getId();
        Long idProduto = foto.getProduto().getId();

        Optional<FotoProduto> fotoPersistida = fotoProdutoRepository.findById(idRestaurante , idProduto);

        if (fotoPersistida.isPresent()) {
            fotoProdutoRepository.delete(fotoPersistida.get());
        }

        return fotoProdutoRepository.save(foto);
    }


}
