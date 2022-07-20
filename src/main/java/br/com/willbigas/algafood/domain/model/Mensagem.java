package br.com.willbigas.algafood.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

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
}
