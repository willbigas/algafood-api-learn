package br.com.willbigas.algafood.api.controller;

import br.com.willbigas.algafood.api.model.request.FotoProdutoRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping(value = "/restaurantes/{idRestaurante}/produtos/{idProduto}/foto")
public class RestauranteProdutoFotoController {


    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void atualizarFoto(@PathVariable Long idRestaurante, @PathVariable Long idProduto, @Valid FotoProdutoRequest request) {

        try {
            String nomeArquivo = UUID.randomUUID().toString() + "_" + request.getArquivo().getOriginalFilename();
            Path pathDestino = Paths.get("/Users/willbigas/Documents/fotos", nomeArquivo);

            System.out.println(request.getDescricao());
            request.getArquivo().transferTo(pathDestino);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
