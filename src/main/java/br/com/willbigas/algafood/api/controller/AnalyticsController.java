package br.com.willbigas.algafood.api.controller;

import br.com.willbigas.algafood.domain.model.view.PeriodoFilter;
import br.com.willbigas.algafood.domain.model.view.VendaDiariaViewDTO;
import br.com.willbigas.algafood.domain.repository.view.VendaDiariaViewDTORepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/analytics")
public class AnalyticsController {

    private final VendaDiariaViewDTORepository vendaDiariaViewDTORepository;

    public AnalyticsController(VendaDiariaViewDTORepository vendaDiariaViewDTORepository) {
        this.vendaDiariaViewDTORepository = vendaDiariaViewDTORepository;
    }

    @GetMapping("/vendas-diarias")
    public List<VendaDiariaViewDTO> listar() {
        return vendaDiariaViewDTORepository.findAll();
    }

    @GetMapping("/vendas-diarias/data-criacao")
    public List<VendaDiariaViewDTO> listarPorData(@RequestBody PeriodoFilter periodoFilter) {
        return vendaDiariaViewDTORepository.findByDataCriacaoBetween(periodoFilter.getDataCriacaoInicial(), periodoFilter.getDataCriacaoFinal());
    }

}
