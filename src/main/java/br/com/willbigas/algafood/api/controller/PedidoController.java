package br.com.willbigas.algafood.api.controller;

import br.com.willbigas.algafood.api.mapper.PedidoMapper;
import br.com.willbigas.algafood.api.model.response.PedidoResponseDTO;
import br.com.willbigas.algafood.api.model.response.PedidoResumidoResponseDTO;
import br.com.willbigas.algafood.domain.service.PedidoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoMapper pedidoMapper;

    public PedidoController(PedidoService pedidoService, PedidoMapper pedidoMapper) {
        this.pedidoService = pedidoService;
        this.pedidoMapper = pedidoMapper;
    }

    @GetMapping
    public List<PedidoResumidoResponseDTO> listar() {
        return pedidoMapper.toPedidoResumidoList(pedidoService.findAll());
    }

    @GetMapping("/{idPedido}")
    public PedidoResponseDTO buscar(@PathVariable Long idPedido) {
        return pedidoMapper.toResponseDTO(pedidoService.buscarOuFalhar(idPedido));
    }

}
