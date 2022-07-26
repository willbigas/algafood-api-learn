package br.com.willbigas.algafood.api.v1.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class PedidoRequestDTO {

    @Valid
    @NotNull
    private RestauranteIDRequestDTO restaurante;

    @Valid
    @NotNull
    private EnderecoRequestDTO enderecoEntrega;

    @Valid
    @NotNull
    private FormaPagamentoIDRequestDTO formaPagamento;

    @Valid
    @Size(min = 1)
    @NotNull
    private List<ItemPedidoRequestDTO> itens;
}
