package br.com.willbigas.algafood.domain.repository.view;

import br.com.willbigas.algafood.domain.model.view.VendaDiariaViewDTO;
import br.com.willbigas.algafood.domain.repository.ReadOnlyRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VendaDiariaViewDTORepository extends ReadOnlyRepository<VendaDiariaViewDTO , Long> {

    List<VendaDiariaViewDTO> findByDataCriacaoBetween(LocalDate dataInicial , LocalDate dataFinal);
}
