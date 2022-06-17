package br.com.willbigas.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class RestauranteModel {

    private Long id;
    private BigDecimal precoFrete;
    private String nome;
    private Boolean ativo;
    private CozinhaModel cozinha;
    private EnderecoModel endereco;
}
