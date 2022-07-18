package br.com.willbigas.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;

public interface FotoStorage {

    void armazenar(NovaFoto novaFotoqqq);

    @Builder
    @Getter
    class NovaFoto {

        private String nomeArquivo;
        private InputStream inputStream;
    }
}
