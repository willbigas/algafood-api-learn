package br.com.willbigas.algafood.domain.repository;

import br.com.willbigas.algafood.domain.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository  extends JpaRepository<Estado , Long> {
}
