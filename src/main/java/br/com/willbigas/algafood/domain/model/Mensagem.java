package br.com.willbigas.algafood.domain.model;

import lombok.*;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@Setter
public class Mensagem {


    private String remetente;

    private String destinatario;
    private List<String> destinatarios;

    @NonNull
    private String assunto;

    @NonNull
    private String corpo;

    @Singular("variavel")
    private Map<String, Object> variaveis;
}
