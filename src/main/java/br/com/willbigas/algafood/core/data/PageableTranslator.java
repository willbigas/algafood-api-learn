package br.com.willbigas.algafood.core.data;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Classe que refaz o mapeamento do Pageable, para ordenar segundo os campos que eu determinar.
 */
public class PageableTranslator {

    public static Pageable translate(Pageable pageable, Map<String, String> fields) {
        List<Sort.Order> orders = pageable.getSort().stream()
                .filter(order -> fields.containsKey(order.getProperty()))
                .map(order -> new Sort.Order(order.getDirection(), fields.get(order.getProperty())))
                .collect(Collectors.toList());

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
    }
}
