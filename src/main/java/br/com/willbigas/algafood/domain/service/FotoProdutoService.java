package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.domain.model.FotoProduto;
import br.com.willbigas.algafood.domain.repository.FotoProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FotoProdutoService {

    private final FotoProdutoRepository repository;

    public FotoProdutoService(FotoProdutoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public FotoProduto salvar(FotoProduto foto) {
        Long idRestaurante = foto.getProduto().getRestaurante().getId();
        Long idProduto = foto.getProduto().getId();

        Optional<FotoProduto> fotoPersistida = repository.findById(idRestaurante , idProduto);

        if (fotoPersistida.isPresent()) {
            repository.delete(fotoPersistida.get());
        }

        return repository.save(foto);
    }


}
