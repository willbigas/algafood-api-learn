package br.com.willbigas.algafood.domain.repository;

import br.com.willbigas.algafood.domain.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;


@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento , Long> {

    @Query("select max(f.dataAtualizacao) from FormaPagamento f")
    OffsetDateTime getUltimaDataAtualizacao();

}
