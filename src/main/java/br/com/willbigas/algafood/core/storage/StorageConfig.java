package br.com.willbigas.algafood.core.storage;

import br.com.willbigas.algafood.domain.service.interfaces.FotoStorageService;
import br.com.willbigas.algafood.domain.service.FotoStorageServiceLocal;
import br.com.willbigas.algafood.domain.service.FotoStorageServiceS3;
import com.amazonaws.services.s3.AmazonS3;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    private final StorageProperties properties;
    private final AmazonS3 amazonS3;


    public StorageConfig(StorageProperties properties, AmazonS3 amazonS3) {
        this.properties = properties;
        this.amazonS3 = amazonS3;
    }

    @Bean
    public FotoStorageService fotoStorageService() {
        switch (properties.getTipo()) {
            case S3:
                return new FotoStorageServiceS3(amazonS3, properties);
            case LOCAL:
                return new FotoStorageServiceLocal(properties);
            default:
                return null;
        }
    }
}
