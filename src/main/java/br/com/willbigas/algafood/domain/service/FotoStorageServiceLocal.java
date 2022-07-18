package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.domain.exception.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FotoStorageServiceLocal implements FotoStorageService {

    @Value("${algafood.storage.local.diretorio-fotos}")
    private Path diretorioDeFotos;

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

    private Path getArquivoPath(String nomeArquivo) {
        return diretorioDeFotos.resolve(Paths.get(nomeArquivo));
    }
}
