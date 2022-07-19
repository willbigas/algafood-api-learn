package br.com.willbigas.algafood.core.storage;

import br.com.willbigas.algafood.domain.service.FotoStorageService;
import br.com.willbigas.algafood.domain.service.FotoStorageServiceLocal;
import br.com.willbigas.algafood.domain.service.FotoStorageServiceS3;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    private final StorageProperties properties;

    public StorageConfig(StorageProperties properties) {
        this.properties = properties;
    }

    @Bean
    public AmazonS3 amazonS3() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(
                properties.getS3().getIdChaveAcesso(), properties.getS3().getChaveAcessoSecreta());


        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(properties.getS3().getRegiao())
                .build();
    }

    @Bean
    public FotoStorageService fotoStorageService() {
        if (StorageProperties.TipoStorage.S3.equals(properties.getTipo())) {
            return new FotoStorageServiceS3();
        }

        if (StorageProperties.TipoStorage.LOCAL.equals(properties.getTipo())) {
            return new FotoStorageServiceLocal();
        }
        return null;
    }
}
