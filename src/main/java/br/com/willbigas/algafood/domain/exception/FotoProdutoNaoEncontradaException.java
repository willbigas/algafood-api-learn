package br.com.willbigas.algafood.domain.exception;

public class FotoProdutoNaoEncontradaException extends EntidadeNaoEncontradaException{

    public FotoProdutoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public FotoProdutoNaoEncontradaException(Long idRestaurante , Long idProduto) {
        this(String.format("Não existe um cadastro de foto do produto com código %d para o restaurante de código %d",
                idProduto, idRestaurante));
    }
}
