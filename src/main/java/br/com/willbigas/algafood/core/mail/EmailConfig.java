package br.com.willbigas.algafood.core.mail;


import br.com.willbigas.algafood.domain.service.FakeEmailService;
import br.com.willbigas.algafood.domain.service.SMTPEmailService;
import br.com.willbigas.algafood.domain.service.SandBoxEmailService;
import br.com.willbigas.algafood.domain.service.TemplateProcessorService;
import br.com.willbigas.algafood.domain.service.interfaces.EmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    private final EmailProperties emailProperties;
    private final TemplateProcessorService templateProcessorService;
    private final AmazonSimpleEmailService amazonSimpleEmailService;

    public EmailConfig(EmailProperties emailProperties, TemplateProcessorService templateProcessorService, AmazonSimpleEmailService amazonSimpleEmailService) {
        this.emailProperties = emailProperties;
        this.templateProcessorService = templateProcessorService;
        this.amazonSimpleEmailService = amazonSimpleEmailService;
    }

    @Bean
    public EmailService envioEmailService() {
        switch (emailProperties.getImpl()) {
            case FAKE:
                return new FakeEmailService(templateProcessorService);
            case SMTP:
                return new SMTPEmailService(amazonSimpleEmailService, templateProcessorService);
            case SANDBOX:
                return new SandBoxEmailService(amazonSimpleEmailService, templateProcessorService, emailProperties);
            default:
                return null;
        }
    }

}
