package br.com.willbigas.algafood.core.openapi;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI algaFoodOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AlgaFood API")
                        .description("API aberta para clientes e restaurantes")
                        .version("v1")
                        .contact(new Contact().name("WBM").email("contato@wbm.com").url("http://google.com"))
                        .license(new License().name("Link da Licença -> Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Código Fonte - GitHub")
                        .url("https://github.com/willbigas/algafood-api-learn"));
    }
}
