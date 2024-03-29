package br.com.willbigas.algafood.api.v1.model.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class RestauranteResponseDTO {

    private Long id;
    private BigDecimal precoFrete;
    private String nome;
    private Boolean ativo;
    private Boolean aberto;
    private CozinhaResponseDTO cozinha;
    private EnderecoResponseDTO endereco;
}
