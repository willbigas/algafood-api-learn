package br.com.willbigas.algafood.domain.repository.spec;

import br.com.willbigas.algafood.domain.model.Pedido;
import br.com.willbigas.algafood.domain.filter.PedidoFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class PedidoSpecification {

    public static Specification<Pedido> usandoFiltro(PedidoFilter filtro) {
        return (root, query, builder) -> {

            if (Pedido.class.equals(query.getResultType())) {
                root.fetch("restaurante").fetch("cozinha");
                root.fetch("cliente");
                root.fetch("formaPagamento");
            }

            List<Predicate> predicates = new ArrayList<Predicate>();

            if (filtro.getIdCliente() != null) {
                predicates.add(builder.equal(root.get("cliente"), filtro.getIdCliente()));
            }

            if (filtro.getIdRestaurante() != null) {
                predicates.add(builder.equal(root.get("restaurante"), filtro.getIdRestaurante()));
            }

            if (filtro.getDataCriacaoInicio() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
            }

            if (filtro.getDataCriacaoFim() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
