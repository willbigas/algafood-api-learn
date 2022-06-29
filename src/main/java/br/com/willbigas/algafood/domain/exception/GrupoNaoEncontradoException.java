package br.com.willbigas.algafood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException{

    public GrupoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public GrupoNaoEncontradoException(Long idGrupoPermissao) {
        this(String.format("Não existe um cadastro de grupo de permissão com código %d", idGrupoPermissao));
    }
}
