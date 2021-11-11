package br.com.willbigas.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada" , "Entidade não encontrada");

    private String uri;
    private String title;

    ProblemType(String uri, String title) {
        this.title = title;
        this.uri = "https://algafood.com.br" + uri;
    }
}
