package br.com.willbigas.algafood.infrastructure.repository.spec;

import br.com.willbigas.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;

public class RestauranteComFreteGratisSpec implements Specification<Restaurante> {

    @Override
    public Specification<Restaurante> and(Specification<Restaurante> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<Restaurante> or(Specification<Restaurante> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<Restaurante> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
        return builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
    }
}
