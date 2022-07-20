package br.com.willbigas.algafood.domain.service.interfaces;

import br.com.willbigas.algafood.domain.model.Mensagem;

import java.nio.charset.Charset;

public interface EmailService {

    void enviarEmail(Mensagem mensagem, Charset charset);
}


