package br.com.willbigas.algafood.domain.exception;

public class EntidadeNaoEncontradaException extends RuntimeException {

    public EntidadeNaoEncontradaException(String message) {
        super(message);
    }
}
