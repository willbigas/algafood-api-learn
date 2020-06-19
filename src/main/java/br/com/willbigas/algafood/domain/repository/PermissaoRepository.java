package br.com.willbigas.algafood.domain.repository;

import br.com.willbigas.algafood.domain.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao , Long> {

}
