package br.com.willbigas.algafood.domain.exception;


public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

    public CidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public CidadeNaoEncontradaException(Long idCidade) {
        this(String.format("Não existe um cadastro de cidade com código %d" , idCidade));
    }
}
