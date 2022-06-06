package br.com.willbigas.algafood.api.model.mixin;

import br.com.willbigas.algafood.domain.model.Cozinha;
import br.com.willbigas.algafood.domain.model.Endereco;
import br.com.willbigas.algafood.domain.model.FormaPagamento;
import br.com.willbigas.algafood.domain.model.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class RestauranteMixin {

    @JsonIgnoreProperties(value = "nome" , allowGetters = true)
    private Cozinha cozinha;

    @JsonIgnore
    private Endereco endereco;

//    @JsonIgnore
    private OffsetDateTime dataCadastro;

//    @JsonIgnore
    private OffsetDateTime dataAtualizacao;

    @JsonIgnore
    private List<Produto> produtos;

    @JsonIgnore
    private List<FormaPagamento> formasPagamento = new ArrayList<>();
}
