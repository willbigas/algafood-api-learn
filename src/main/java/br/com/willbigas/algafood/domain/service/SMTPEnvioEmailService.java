package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.core.email.EmailProperties;
import br.com.willbigas.algafood.domain.exception.EmailException;
import br.com.willbigas.algafood.domain.service.interfaces.EnvioEmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class SMTPEnvioEmailService implements EnvioEmailService {

    private final JavaMailSender mailSender;
    private final EmailProperties emailProperties;

    public SMTPEnvioEmailService(JavaMailSender mailSender, EmailProperties emailProperties) {
        this.mailSender = mailSender;
        this.emailProperties = emailProperties;
    }

    @Override
    public void enviar(Mensagem mensagem) {
      try {
          MimeMessage mimeMessage = mailSender.createMimeMessage();
          MimeMessageHelper helper = new MimeMessageHelper(mimeMessage , "UTF-8");

          helper.setFrom(emailProperties.getRemetente());
          helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
          helper.setSubject(mensagem.getAssunto());
          helper.setText(mensagem.getCorpo(), true);

          mailSender.send(mimeMessage);
      }catch (Exception e) {
          throw new EmailException("Não foi possivel enviar e-mail" , e);
      }
    }
}
