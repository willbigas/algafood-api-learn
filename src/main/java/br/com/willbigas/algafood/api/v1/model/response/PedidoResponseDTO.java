package br.com.willbigas.algafood.api.v1.model.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoResponseDTO {

    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataEntrega;
    private OffsetDateTime dataCancelamento;
    private RestauranteIDResponseDTO restaurante;
    private UsuarioResponseDTO cliente;
    private FormaPagamentoResponseDTO formaPagamento;
    private EnderecoResponseDTO enderecoEntrega;
    private List<ItemPedidoResponseDTO> itens;
}
