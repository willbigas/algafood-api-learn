package br.com.willbigas.algafood.domain.repository;

import br.com.willbigas.algafood.domain.model.Restaurante;
import br.com.willbigas.algafood.domain.filter.RestauranteFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteRepositoryCustomizedQueries {

    List<Restaurante> findComCriteria(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

    List<Restaurante> findComJPQL(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

    List<Restaurante> findComSpecification(String nome);

    Page<Restaurante> findWithPageAndSortCustomize(RestauranteFilter restauranteFilter , Pageable page);
}
