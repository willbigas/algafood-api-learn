package br.com.willbigas.algafood.domain.repository.filter;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteFilter implements Serializable {

    private Long id;
    private String nome;
    private BigDecimal taxaFreteInicial;
    private BigDecimal taxaFreteFinal;
}
