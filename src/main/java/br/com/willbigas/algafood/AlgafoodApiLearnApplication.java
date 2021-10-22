package br.com.willbigas.algafood;

import br.com.willbigas.algafood.infrastructure.repository.CustomJPARepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJPARepositoryImpl.class)
public class AlgafoodApiLearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlgafoodApiLearnApplication.class, args);
    }

}
