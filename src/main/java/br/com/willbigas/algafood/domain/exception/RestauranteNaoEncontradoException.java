package br.com.willbigas.algafood.domain.exception;


public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

    public RestauranteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public RestauranteNaoEncontradoException(Long idRestaurante) {
        this(String.format("Não existe um cadastro de restaurante com código %d" , idRestaurante));
    }
}
