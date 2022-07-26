package br.com.willbigas.algafood.api.v1.model.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ProdutoRequestDTO {

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private Boolean ativo;

    private RestauranteIDRequestDTO restaurante;
}
