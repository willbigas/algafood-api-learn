package br.com.willbigas.algafood.api.model.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ProdutoRequestSimplificadoDTO {

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private Boolean ativo;

}
