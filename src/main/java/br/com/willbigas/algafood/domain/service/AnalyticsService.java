package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.domain.exception.ReportException;
import br.com.willbigas.algafood.domain.filter.VendaDiariaFilter;
import br.com.willbigas.algafood.domain.model.view.VendaDiariaViewDTO;
import br.com.willbigas.algafood.domain.repository.view.VendaDiariaViewDTORepository;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class AnalyticsService {

    private final VendaDiariaViewDTORepository vendaDiariaViewDTORepository;

    public AnalyticsService(VendaDiariaViewDTORepository vendaDiariaViewDTORepository) {
        this.vendaDiariaViewDTORepository = vendaDiariaViewDTORepository;
    }

    public List<VendaDiariaViewDTO> findAll() {
        return vendaDiariaViewDTORepository.findAll();
    }

    public List<VendaDiariaViewDTO> findByDataCriacaoBetween(LocalDate dataCriacaoInicio, LocalDate dataCriacaoFim) {
        return vendaDiariaViewDTORepository.findByDataCriacaoBetween(dataCriacaoInicio, dataCriacaoFim);
    }

    public byte[] emitirVendasDiariasEmPDF(VendaDiariaFilter filter) {
        try {
            InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/VendasDiarias.jasper");

            List<VendaDiariaViewDTO> vendasDiarias = findAll();

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(vendasDiarias);

            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, dataSource);
            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            throw new ReportException("Não foi possivel emitir relatório de vendas diárias", e);
        }
    }


}
