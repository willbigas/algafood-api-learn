package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.domain.exception.NegocioException;
import br.com.willbigas.algafood.domain.exception.PedidoNaoEncontradoException;
import br.com.willbigas.algafood.domain.model.*;
import br.com.willbigas.algafood.domain.repository.PedidoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final RestauranteService restauranteService;
    private final CidadeService cidadeService;
    private final UsuarioService usuarioService;
    private final FormaPagamentoService formaPagamentoService;

    public PedidoService(PedidoRepository pedidoRepository, RestauranteService restauranteService, CidadeService cidadeService, UsuarioService usuarioService, FormaPagamentoService formaPagamentoService) {
        this.pedidoRepository = pedidoRepository;
        this.restauranteService = restauranteService;
        this.cidadeService = cidadeService;
        this.usuarioService = usuarioService;
        this.formaPagamentoService = formaPagamentoService;
    }

    public List<Pedido> findAll(Specification<Pedido> pedidoSpecification) {
        return pedidoRepository.findAll(pedidoSpecification);
    }

    public Page<Pedido> findAll(Specification<Pedido> pedidoSpecification , Pageable pageable) {
        return pedidoRepository.findAll(pedidoSpecification , pageable);
    }

    public Pedido buscarOuFalhar(String codigoPedido) {
        return pedidoRepository.findByCodigo(codigoPedido)
                .orElseThrow(() -> new PedidoNaoEncontradoException(codigoPedido));
    }

    @Transactional
    public Pedido emitir(Pedido pedido) {
        validarPedido(pedido);
        validarItens(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotal();

        return pedidoRepository.save(pedido);
    }

    private void validarPedido(Pedido pedido) {
        Cidade cidade = cidadeService.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
        Usuario cliente = usuarioService.buscarOuFalhar(pedido.getCliente().getId());
        Restaurante restaurante = restauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());

        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);

        if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
            throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
                    formaPagamento.getDescricao()));
        }
    }

    private void validarItens(Pedido pedido) {
        pedido.getItens().forEach(item -> {
            Produto produto = restauranteService.buscarOuFalhar(pedido.getRestaurante().getId(), item.getProduto().getId());

            item.setPedido(pedido);
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
        });
    }


}
