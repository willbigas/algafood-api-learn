package br.com.willbigas.algafood.api.mapper;

import br.com.willbigas.algafood.api.model.request.PedidoRequestDTO;
import br.com.willbigas.algafood.api.model.response.PedidoResponseDTO;
import br.com.willbigas.algafood.api.model.response.PedidoResumidoResponseDTO;
import br.com.willbigas.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    private final ModelMapper modelMapper;

    public PedidoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PedidoResponseDTO toResponseDTO(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResponseDTO.class);
    }

    public List<PedidoResponseDTO> toList(Collection<Pedido> pedidos) {
        return pedidos.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public PedidoResumidoResponseDTO toPedidoResumidoResponseDTO(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResumidoResponseDTO.class);
    }

    public List<PedidoResumidoResponseDTO> toPedidoResumidoList(Collection<Pedido> pedidos) {
        return pedidos.stream()
                .map(this::toPedidoResumidoResponseDTO)
                .collect(Collectors.toList());
    }

    public Pedido toPedido(PedidoRequestDTO pedidoRequestDTO) {
        return modelMapper.map(pedidoRequestDTO, Pedido.class);
    }

    public void copy(PedidoRequestDTO pedidoRequestDTO, Pedido pedido) {
        modelMapper.map(pedidoRequestDTO, pedido);
    }
}
