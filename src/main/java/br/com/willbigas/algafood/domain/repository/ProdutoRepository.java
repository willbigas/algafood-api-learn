package br.com.willbigas.algafood.domain.repository;

import br.com.willbigas.algafood.domain.model.Produto;
import br.com.willbigas.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByRestaurante(Restaurante restaurante);

    List<Produto> findByRestauranteAndAtivoIsTrue(Restaurante restaurante);

}
