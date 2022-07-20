package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.domain.exception.EmailException;
import br.com.willbigas.algafood.domain.model.Mensagem;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Collections;

@Service
@Log4j2
public class EmailService {

    private final AmazonSimpleEmailService amazonSimpleEmailService;


    @Value("${algafood.aws.ses.remetente-default}")
    private String remetentePadrao;

    public EmailService(AmazonSimpleEmailService amazonSimpleEmailService) {
        this.amazonSimpleEmailService = amazonSimpleEmailService;
    }

    @Async
    public void enviarEmail(Mensagem mensagem , Charset charset) {

        try {


            Destination destination = null;

            if (mensagem.getRemetente() == null) {
                mensagem.setRemetente(remetentePadrao);
            }

            if (mensagem.getDestinatario() != null) {
                destination = new Destination(Collections.singletonList(mensagem.getDestinatario()));
            }

            if (mensagem.getDestinatarios() != null) {
                destination = new Destination(mensagem.getDestinatarios());
            }

            Message message = new Message()
                    .withBody(new Body().withHtml(new Content().withCharset(charset.toString()).withData(mensagem.getCorpo())))
                    .withSubject(new Content().withCharset(charset.toString()).withData(mensagem.getAssunto()));

            SendEmailRequest sendEmailRequest = new SendEmailRequest(mensagem.getRemetente(), destination, message);

            SendEmailResult result = amazonSimpleEmailService.sendEmail(sendEmailRequest);
            log.info("Email enviado com sucesso -> Message ID -> " + result.getMessageId());
        } catch (Exception e) {
            throw new EmailException("Houve um problema no envio de e-mail", e);
        }
    }

}
