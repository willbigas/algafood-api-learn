package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.domain.model.Mensagem;
import br.com.willbigas.algafood.domain.service.interfaces.EmailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;

@Log4j2
public class FakeEmailService implements EmailService {

    private final TemplateProcessorService templateProcessorService;


    public FakeEmailService(TemplateProcessorService templateProcessorService) {
        this.templateProcessorService = templateProcessorService;
    }


    @Override
    public void enviarEmail(Mensagem mensagem, Charset charset) {
        String corpo = templateProcessorService.processarTemplate(mensagem);
        log.info("[FAKE E-MAIL] Para: {}\n{}", mensagem.getDestinatarios() != null ? mensagem.getDestinatarios() : mensagem.getDestinatario(), corpo);

    }
}
