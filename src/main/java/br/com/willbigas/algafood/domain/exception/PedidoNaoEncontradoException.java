package br.com.willbigas.algafood.domain.exception;


public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public PedidoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public PedidoNaoEncontradoException(Long idPedido) {
        this(String.format("Não existe um cadastro de pedido com código %d" , idPedido));
    }
}
