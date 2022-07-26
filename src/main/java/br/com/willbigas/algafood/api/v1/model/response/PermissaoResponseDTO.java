package br.com.willbigas.algafood.api.v1.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissaoResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
}
