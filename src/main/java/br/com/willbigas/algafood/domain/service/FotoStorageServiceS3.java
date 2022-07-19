package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.core.storage.StorageProperties;
import br.com.willbigas.algafood.domain.exception.StorageException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class FotoStorageServiceS3 implements FotoStorageService {

    private final AmazonS3 amazonS3;
    private final StorageProperties storageProperties;

    public FotoStorageServiceS3(AmazonS3 amazonS3, StorageProperties storageProperties) {
        this.amazonS3 = amazonS3;
        this.storageProperties = storageProperties;
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {

            String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(novaFoto.getContentType());

            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    storageProperties.getS3().getBucket(),
                    caminhoArquivo,
                    novaFoto.getInputStream(),
                    objectMetadata
            ).withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Não foi possivel enviar arquivo para amazonS3", e);
        }

    }

    @Override
    public InputStream recuperar(String nomeArquivo) {
        return null;
    }

    @Override
    public void remover(String nomeArquivo) {

    }

    private String getCaminhoArquivo(String nomeArquivo) {
        return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
    }


}
