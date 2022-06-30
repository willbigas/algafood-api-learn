package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.domain.exception.PedidoNaoEncontradoException;
import br.com.willbigas.algafood.domain.model.Pedido;
import br.com.willbigas.algafood.domain.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarOuFalhar(Long idPedido) {
        return pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new PedidoNaoEncontradoException(idPedido));
    }

}
