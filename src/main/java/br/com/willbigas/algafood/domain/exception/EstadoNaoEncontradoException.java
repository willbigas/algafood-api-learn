package br.com.willbigas.algafood.domain.exception;


public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public EstadoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public EstadoNaoEncontradoException(Long idEstado) {
        this(String.format("Não existe um cadastro de estado com código %d" , idEstado));
    }
}
