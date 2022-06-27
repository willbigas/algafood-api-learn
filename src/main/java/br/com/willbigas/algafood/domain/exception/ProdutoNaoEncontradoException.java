package br.com.willbigas.algafood.domain.exception;


public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public ProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public ProdutoNaoEncontradoException(Long idProduto) {
        this(String.format("Não existe um cadastro de produto com o código %d" , idProduto));
    }
}
