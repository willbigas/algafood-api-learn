package br.com.willbigas.algafood.domain.exception;


public class CidadeNaoEncontradoException extends EntidadeNaoEncontradaException {

    public CidadeNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public CidadeNaoEncontradoException(Long idCidade) {
        this(String.format("Não existe um cadastro de cidade com código %d" , idCidade));
    }
}
