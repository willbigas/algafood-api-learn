package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.domain.exception.EmailException;
import br.com.willbigas.algafood.domain.model.Mensagem;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.Locale;

@Service
public class TemplateProcessorService {


    private final Configuration freeMarkerConfig;

    public TemplateProcessorService(Configuration freeMarkerConfig) {
        this.freeMarkerConfig = freeMarkerConfig;
    }

    public String processarTemplate(Mensagem mensagem) {
        try {
            freeMarkerConfig.setLocale(Locale.getDefault());
            Template template = freeMarkerConfig.getTemplate(mensagem.getCorpo());
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis());
        } catch (Exception e) {
            throw new EmailException("Não foi possível montar o template do e-mail", e);
        }
    }
}
