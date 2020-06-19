package br.com.willbigas.algafood.domain.repository;

import br.com.willbigas.algafood.domain.model.Estado;
import br.com.willbigas.algafood.domain.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstadoRepository  extends JpaRepository<Estado , Long> {

}
