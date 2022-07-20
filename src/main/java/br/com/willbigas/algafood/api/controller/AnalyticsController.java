package br.com.willbigas.algafood.api.controller;

import br.com.willbigas.algafood.domain.filter.VendaDiariaFilter;
import br.com.willbigas.algafood.domain.model.Mensagem;
import br.com.willbigas.algafood.domain.model.view.PeriodoFilter;
import br.com.willbigas.algafood.domain.model.view.VendaDiariaViewDTO;
import br.com.willbigas.algafood.domain.service.AnalyticsService;
import br.com.willbigas.algafood.domain.service.EmailService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping(value = "/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;
    private final EmailService emailService;


    public AnalyticsController(AnalyticsService analyticsService, EmailService emailService) {
        this.analyticsService = analyticsService;
        this.emailService = emailService;
    }

    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VendaDiariaViewDTO> listar() {
        return analyticsService.findAll();
    }

    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> listarEmPDF(@RequestBody(required = false) VendaDiariaFilter filter) {

        byte[] bytesInPDF = analyticsService.emitirVendasDiariasEmPDF(filter);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf")
                .body(bytesInPDF);
    }

    @GetMapping("/vendas-diarias/data-criacao")
    public List<VendaDiariaViewDTO> listarPorData(@RequestBody PeriodoFilter periodoFilter) {
        return analyticsService.findByDataCriacaoBetween(periodoFilter.getDataCriacaoInicial(), periodoFilter.getDataCriacaoFinal());
    }

    @GetMapping("/enviar-email")
    public void enviarEmail() {

        Mensagem mensagem = Mensagem.builder()
                .remetente("will.bigas@gmail.com")
                .destinatario("will.bigas@gmail.com")
                .assunto("Teste de Assunto enviado por e-mail")
                .corpo("<strong>Corpo</strong> do e-mail de <i>teste</i>")
                .build();

        emailService.enviarEmail(mensagem, StandardCharsets.UTF_8);
    }

}
