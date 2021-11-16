package br.com.willbigas.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    MENSAGEM_IMCOMPREENSIVEL("/mensagem-imcompreensivel", "Mensagem imcompreensível"),
    DADOS_INVALIDOS("/dados-invalidos", "Dados Inválidos"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado" , "Recurso não encontrado"),
    ENTIDADE_EM_USO("/entidade-em-uso" , "Entidade em Uso"),
    ERRO_NEGOCIO("/erro-negocio" , "Excessão de negócio"),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema");


    private String uri;
    private String title;

    ProblemType(String uri, String title) {
        this.title = title;
        this.uri = "https://algafood.com.br" + uri;
    }

}
