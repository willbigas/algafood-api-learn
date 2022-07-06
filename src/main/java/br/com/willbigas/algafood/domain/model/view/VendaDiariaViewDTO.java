package br.com.willbigas.algafood.domain.model.view;

import lombok.Getter;
import org.springframework.data.annotation.Immutable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


/**
 * CREATE  OR REPLACE VIEW vw_relatorio_venda AS
 * select
 * p.id as id,
 * date(p.data_criacao) as data_criacao,
 * count(*) as total_venda,
 * sum(p.valor_total) as total_faturado,
 * p.restaurante_id as id_restaurante
 * from pedido p
 * where p.status  in ('CONFIRMADO' , 'ENTREGUE')
 * group by p.data_criacao , p.restaurante_id
 */

@Entity
@Table(name = "vw_relatorio_venda")
@Immutable
@Getter
public class VendaDiariaViewDTO {

    @Id
    private Long id;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @Column(name = "total_venda")
    private Long totalVenda;

    @Column(name = "total_faturado")
    private BigDecimal totalFaturado;

    @Column(name = "id_restaurante")
    private Long idRestaurante;
}
