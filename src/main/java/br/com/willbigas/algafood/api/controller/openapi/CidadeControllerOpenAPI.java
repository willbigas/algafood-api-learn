package br.com.willbigas.algafood.api.controller.openapi;

import br.com.willbigas.algafood.api.exceptionhandler.Problem;
import br.com.willbigas.algafood.domain.model.Cidade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Cidade", description = "Gerencia as cidades")
public interface CidadeControllerOpenAPI {

    @Operation(summary = "Lista as cidades", tags = {"Cidade"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, verifique o corpo ou path da requisição",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))}),
            @ApiResponse(responseCode = "500", description = "Erro inesperado no servidor"),
    })
    ResponseEntity<List<Cidade>> listar();

    @Operation(summary = "Busca uma cidade por ID", tags = {"Cidade"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Cidade.class))}),
            @ApiResponse(responseCode = "400", description = "ID da cidade inválida", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))}),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))}),
            @ApiResponse(responseCode = "500", description = "Erro inesperado no servidor", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))}),
    })
    Cidade buscar(@Parameter(description = "ID de uma cidade", example = "123") Long id);

    @Operation(summary = "Cadastra uma cidade", tags = {"Cidade"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cidade cadastrada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Cidade.class))}),
    })
    Cidade adicionar(@Parameter(name = "Corpo", description = "Representação de uma nova cidade") Cidade cidade);

    @Operation(summary = "Atualiza uma cidade por ID", tags = {"Cidade"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cidade atualizada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Cidade.class))}),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))}),
    })
    Cidade atualizar(@Parameter(description = "ID de uma cidade", example = "123") Long id, @Parameter(name = "Corpo", description = "Representação de uma nova cidade") Cidade cidade);

    @Operation(summary = "Exclui uma cidade por ID", tags = {"Cidade"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cidade excluida"),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))}),
    })
    void remover(@Parameter(description = "ID de uma cidade", example = "123") Long id);

}
