package br.com.willbigas.algafood.api.v1.model.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
//@JsonFilter("pedidoFilter")
public class PedidoResumidoResponseDTO {

    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime dataCriacao;
    private RestauranteIDResponseDTO restaurante;
//    private UsuarioResponseDTO cliente;
    private String nomeCliente;
}
