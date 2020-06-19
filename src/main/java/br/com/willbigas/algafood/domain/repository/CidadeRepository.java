package br.com.willbigas.algafood.domain.repository;

import br.com.willbigas.algafood.domain.model.Cidade;
import br.com.willbigas.algafood.domain.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade , Long> {

}
