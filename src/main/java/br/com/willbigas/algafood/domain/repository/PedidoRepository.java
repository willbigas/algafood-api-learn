package br.com.willbigas.algafood.domain.repository;

import br.com.willbigas.algafood.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("from Pedido p join fetch p.cliente join fetch p.restaurante r join fetch p.formaPagamento join fetch r.cozinha c")
    List<Pedido> findAll();

}
