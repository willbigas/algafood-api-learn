package br.com.willbigas.algafood.domain.repository;

import br.com.willbigas.algafood.domain.model.FotoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FotoProdutoRepository extends JpaRepository<FotoProduto, Long> {

    @Query("select f from FotoProduto f join f.produto p where p.restaurante.id = :idRestaurante and f.produto.id = :idProduto")
    Optional<FotoProduto> findById(Long idRestaurante, Long idProduto);

}
