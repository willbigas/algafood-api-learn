package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.core.storage.StorageProperties;
import br.com.willbigas.algafood.domain.exception.StorageException;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//@Service
public class FotoStorageServiceLocal implements FotoStorageService {

//    @Value("${algafood.storage.local.diretorio-fotos}")
//    private Path diretorioDeFotos;

    private final StorageProperties storageProperties;

    public FotoStorageServiceLocal(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {

        try {
            Path caminho = getArquivoPath(novaFoto.getNomeArquivo());
            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(caminho));
        } catch (Exception e) {
            throw new StorageException("Não foi possivel armazenar arquivo" , e);
        }

    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            Path arquivoPath = getArquivoPath(nomeArquivo);
            Files.deleteIfExists(arquivoPath);
        } catch (Exception e) {
            throw new StorageException("Não foi possivel excluir arquivo" , e);
        }
    }

    @Override
    public InputStream recuperar(String nomeArquivo) {
        try {
            Path arquivoPath = getArquivoPath(nomeArquivo);
            return Files.newInputStream(arquivoPath);
        } catch (Exception e) {
            throw new StorageException("Não foi possível recuperar arquivo.", e);
        }
    }

    private Path getArquivoPath(String nomeArquivo) {
        return storageProperties.getLocal().getDiretorioFotos().resolve(Paths.get(nomeArquivo));
    }
}
