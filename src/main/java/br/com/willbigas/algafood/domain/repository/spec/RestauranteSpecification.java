package br.com.willbigas.algafood.domain.repository.spec;

import br.com.willbigas.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RestauranteSpecification {

    public static Specification<Restaurante> comFreteGratis() {
        return (root , query, builder) -> builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
    }

    public static Specification<Restaurante> comNomeSemelhante(String nome) {
        return (root, criteriaQuery, builder) -> builder.like(root.get("nome"), "%" + nome + "%");
    }
}
