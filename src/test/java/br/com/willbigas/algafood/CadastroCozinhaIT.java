package br.com.willbigas.algafood;

import br.com.willbigas.algafood.domain.model.Cozinha;
import br.com.willbigas.algafood.domain.repository.CozinhaRepository;
import br.com.willbigas.algafood.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CadastroCozinhaIT {

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @LocalServerPort
    private int port;

    private static final Cozinha cozinhaAmericana = new Cozinha("Americana");
    private static final Cozinha cozinhaTailandesa = new Cozinha("Tailandesa");

    private static final List<Cozinha> cozinhas = Arrays.asList(cozinhaAmericana, cozinhaTailandesa);

    public static final String PATH_COZINHAS = "/cozinhas";

    public static final int COZINHA_ID_EXISTENTE = 2;
    public static final int COZINHA_ID_INEXISTENTE = 100;

    @BeforeEach
    public void beforeEach() {
        enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.basePath = PATH_COZINHAS;
        RestAssured.port = port;

        databaseCleaner.clearTables();
        prepararDados();
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinhas() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }


    @Test
    public void deveConter2Cozinhas_QuandoConsultarCozinhas() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("", hasSize(cozinhas.size()))
                .body("nome", hasItems(cozinhaAmericana.getNome(), cozinhaTailandesa.getNome()));
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarCozinha() {
        given()
                .body(getContentFromResource("/content.json"))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarRespostaEStatusCorretosQuandoConsultarCozinhaExistente() {
        given()
                .pathParam("cozinhaId", COZINHA_ID_EXISTENTE)
                .accept(ContentType.JSON)
                .when()
                .get("/{cozinhaId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", equalTo(cozinhaTailandesa.getNome()));
    }

    @Test
    public void deveRetornarStatus404QuandoConsultarCozinhaInexistente() {
        given()
                .pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
                .accept(ContentType.JSON)
                .when()
                .get("/{cozinhaId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepararDados() {
        cozinhaRepository.saveAll(cozinhas);
    }

    public String getContentFromResource(String resourceName) {
        try {
            InputStream stream = this.getClass().getResourceAsStream(resourceName);
            return StreamUtils.copyToString(stream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
