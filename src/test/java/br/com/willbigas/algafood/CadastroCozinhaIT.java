package br.com.willbigas.algafood;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CadastroCozinhaIT {

    @LocalServerPort
    private int port;

    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinhas() {
        enableLoggingOfRequestAndResponseIfValidationFails();

        given()
                .basePath("/cozinhas")
                .port(port)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }


    @Test
    public void deveConter9Cozinhas_QuandoConsultarCozinhas() {
        enableLoggingOfRequestAndResponseIfValidationFails();

                given()
                    .basePath("/cozinhas")
                    .port(port)
                    .accept(ContentType.JSON)
                .when()
                    .get()
                .then()
                    .body("", hasSize(9))
                    .body("nome", hasItems("Indiana", "Tailandesa"));
    }


}
