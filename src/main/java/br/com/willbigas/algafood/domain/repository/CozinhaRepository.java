package br.com.willbigas.algafood.domain.repository;

import br.com.willbigas.algafood.domain.model.Cozinha;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CozinhaRepository  extends CustomJPARepository<Cozinha , Long> {

    List<Cozinha> findAllByNomeContaining(String nome);

    Optional<Cozinha> findByNome(String nome);

    boolean existsByNome(String nome);

}
