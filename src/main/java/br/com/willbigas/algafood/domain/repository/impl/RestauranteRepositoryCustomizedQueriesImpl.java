package br.com.willbigas.algafood.domain.repository.impl;

import br.com.willbigas.algafood.domain.model.Restaurante;
import br.com.willbigas.algafood.domain.repository.RestauranteRepositoryCustomized;
import br.com.willbigas.algafood.domain.repository.RestauranteRepositoryCustomizedQueries;
import br.com.willbigas.algafood.domain.filter.RestauranteFilter;
import br.com.willbigas.algafood.domain.repository.spec.RestauranteSpecification;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RestauranteRepositoryCustomizedQueriesImpl implements RestauranteRepositoryCustomizedQueries {

    @PersistenceContext
    private EntityManager manager;

    private final RestauranteRepositoryCustomized restauranteRepository;

    @Lazy
    public RestauranteRepositoryCustomizedQueriesImpl(RestauranteRepositoryCustomized restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @Override
    public List<Restaurante> findComCriteria(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
        Root<Restaurante> root = criteria.from(Restaurante.class);

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(nome)) {
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
        }

        if (taxaFreteInicial != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
        }

        if (taxaFreteFinal != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
        }

        criteria.where(predicates.toArray(new Predicate[0]));


        TypedQuery<Restaurante> query = manager.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public List<Restaurante> findComSpecification(String nome) {
        return restauranteRepository.findAll(RestauranteSpecification.comFreteGratis().and(RestauranteSpecification.comNomeSemelhante(nome)));
    }

    public List<Restaurante> findComJPQL(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

        StringBuilder jpql = new StringBuilder();
        jpql.append("from Restaurante where 1=1 ");

        Map<String, Object> parametros = new HashMap<>();

        if (StringUtils.hasLength(nome)) {
            jpql.append("and nome like :nome ");
            parametros.put("nome", "%" + nome + "%");
        }

        if (taxaFreteInicial != null) {
            jpql.append("and taxaFrete >= :taxaInicial ");
            parametros.put("taxaInicial", taxaFreteInicial);
        }

        if (taxaFreteFinal != null) {
            jpql.append("and taxaFrete <= :taxaFinal ");
            parametros.put("taxaFinal", taxaFreteFinal);
        }

        TypedQuery<Restaurante> query = manager.createQuery(jpql.toString(), Restaurante.class);

        parametros.forEach((chave, valor) -> query.setParameter(chave, valor));

        return query.getResultList();
    }


    /**
     * Implementação mais genérica possivel de Consulta por Filter com Paginação e Ordenação
     *
     * @param filter
     * @param pageable
     * @return
     */
    @Override
    public Page<Restaurante> findWithPageAndSortCustomize(RestauranteFilter filter, Pageable pageable) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);

        Root<Restaurante> root = criteria.from(Restaurante.class);

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(filter.getNome())) {
            predicates.add(builder.like(root.get("nome"), "%" + filter.getNome() + "%"));
        }

        if (filter.getTaxaFreteInicial() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), filter.getTaxaFreteInicial()));
        }

        if (filter.getTaxaFreteFinal() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), filter.getTaxaFreteInicial()));
        }

        criteria.orderBy(QueryUtils.toOrders(pageable.getSort(), root, builder));
        criteria.where(predicates.toArray(new Predicate[0]));

        Query query = manager.createQuery(criteria);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        return new PageImpl<>(query.getResultList(), pageable, getTotalCount(builder, predicates.toArray(new Predicate[0])));
    }

    private Long getTotalCount(CriteriaBuilder criteriaBuilder, Predicate... predicateArray) {
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Restaurante> root = criteriaQuery.from(Restaurante.class);

        criteriaQuery.select(criteriaBuilder.count(root));
        criteriaQuery.where(predicateArray);

        return manager.createQuery(criteriaQuery).getSingleResult();
    }
}
