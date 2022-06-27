package br.com.willbigas.algafood.domain.exception;


public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public FormaPagamentoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public FormaPagamentoNaoEncontradaException(Long idFormaPagamento) {
        this(String.format("Não existe um cadastro de forma de pagamento com o código %d" , idFormaPagamento));
    }
}
