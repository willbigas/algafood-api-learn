package br.com.willbigas.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EntidadeEmUsoException extends RuntimeException {

    public EntidadeEmUsoException(String message) {
        super(message);
    }
}
