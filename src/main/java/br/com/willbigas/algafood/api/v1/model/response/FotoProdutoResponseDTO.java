package br.com.willbigas.algafood.api.v1.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoResponseDTO {

    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;
}
