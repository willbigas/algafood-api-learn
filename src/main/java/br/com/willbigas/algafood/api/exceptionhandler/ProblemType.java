package br.com.willbigas.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    MENSAGEM_IMCOMPREENSIVEL("/mensagem-imcompreensivel", "Mensagem imcompreensível"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada" , "Entidade não encontrada"),
    ENTIDADE_EM_USO("/entidade-em-uso" , "Entidade em Uso"),
    ERRO_NEGOCIO("/erro-negocio" , "Excessão de negócio");

    private String uri;
    private String title;

    ProblemType(String uri, String title) {
        this.title = title;
        this.uri = "https://algafood.com.br" + uri;
    }

}
