package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.domain.exception.FotoProdutoNaoEncontradaException;
import br.com.willbigas.algafood.domain.model.FotoProduto;
import br.com.willbigas.algafood.domain.repository.FotoProdutoRepository;
import br.com.willbigas.algafood.domain.service.interfaces.FotoStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

import static br.com.willbigas.algafood.domain.service.interfaces.FotoStorageService.*;

@Service
public class FotoProdutoService {

    private final FotoProdutoRepository repository;
    private final FotoStorageService fotoStorage;

    public FotoProdutoService(FotoProdutoRepository repository, FotoStorageService fotoStorage) {
        this.repository = repository;
        this.fotoStorage = fotoStorage;
    }

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivos) {
        Long idRestaurante = foto.getProduto().getRestaurante().getId();
        Long idProduto = foto.getProduto().getId();
        String novoNomeDoArquivo = fotoStorage.gerarNomeArquivo(foto.getNomeArquivo());
        String nomeFotoPersistida = null;

        Optional<FotoProduto> fotoPersistida = repository.findById(idRestaurante, idProduto);

        if (fotoPersistida.isPresent()) {
            nomeFotoPersistida = fotoPersistida.get().getNomeArquivo();
            repository.delete(fotoPersistida.get());
        }

        foto.setNomeArquivo(novoNomeDoArquivo);
        foto = repository.save(foto);
        repository.flush();

        NovaFoto novaFoto = NovaFoto.builder()
                .nomeArquivo(foto.getNomeArquivo())
                .contentType(foto.getContentType())
                .size(foto.getTamanho())
                .inputStream(dadosArquivos)
                .build();

        fotoStorage.substituir(nomeFotoPersistida, novaFoto);

        return foto;
    }

    public FotoRecuperada recuperar(String nomeArquivo) {
        return fotoStorage.recuperar(nomeArquivo);
    }

    @Transactional
    public void excluir(Long restauranteId, Long produtoId) {
        FotoProduto foto = buscarOuFalhar(restauranteId, produtoId);

        repository.delete(foto);
        repository.flush();

        fotoStorage.remover(foto.getNomeArquivo());
    }

    public FotoProduto buscarOuFalhar(Long idRestaurante, Long idProduto) {
        return repository.findById(idRestaurante, idProduto)
                .orElseThrow(() -> new FotoProdutoNaoEncontradaException(idRestaurante, idProduto));
    }


}
