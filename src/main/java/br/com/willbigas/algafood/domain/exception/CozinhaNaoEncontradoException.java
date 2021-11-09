package br.com.willbigas.algafood.domain.exception;


public class CozinhaNaoEncontradoException extends EntidadeNaoEncontradaException {

    public CozinhaNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public CozinhaNaoEncontradoException(Long idCozinha) {
        this(String.format("Não existe um cadastro de cozinha com código %d" , idCozinha));
    }
}
