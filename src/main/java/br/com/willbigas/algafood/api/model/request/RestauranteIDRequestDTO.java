package br.com.willbigas.algafood.api.model.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteIDRequestDTO {

    private Long id;
    private String nome;
}
