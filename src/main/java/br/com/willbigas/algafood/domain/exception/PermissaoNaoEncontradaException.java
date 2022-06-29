package br.com.willbigas.algafood.domain.exception;

public class PermissaoNaoEncontradaException  extends EntidadeNaoEncontradaException{

    public PermissaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public PermissaoNaoEncontradaException(Long idPermissao) {
        this(String.format("Não existe um cadastro de permissão com código %d", idPermissao));
    }
}
