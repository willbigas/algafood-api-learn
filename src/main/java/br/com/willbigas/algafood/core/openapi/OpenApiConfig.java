package br.com.willbigas.algafood.core.openapi;

import br.com.willbigas.algafood.api.exceptionhandler.Problem;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomiser;
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

    @Bean
    public OpenApiCustomiser addProblemClass() {
        ResolvedSchema resolvedSchema = ModelConverters.getInstance()
                .readAllAsResolvedSchema(new AnnotatedType(Problem.class));
        return openApi -> openApi.schema(resolvedSchema.schema.getName(), resolvedSchema.schema);
    }


    @Bean
    public OpenApiCustomiser createDefaultResponseCodes() {

        return openApi -> openApi
                .getPaths()
                .values()
                .forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
                    ApiResponses apiResponses = operation.getResponses();

                    ApiResponse apiResponse500 = getAPIResponse("Erro Inesperado interno do servidor");
                    apiResponses.addApiResponse("500", apiResponse500);
                }));
    }

    private ApiResponse getAPIResponse(String description) {
        return new ApiResponse()
                .description(description)
                .content(new Content()
                        .addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE, new MediaType()));
    }
}
