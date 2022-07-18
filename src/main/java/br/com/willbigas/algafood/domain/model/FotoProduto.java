package br.com.willbigas.algafood.domain.model;

import br.com.willbigas.algafood.core.validation.Groups;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class FotoProduto implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "produto_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Produto produto;

    private String nomeArquivo;

    private String descricao;

    private String contentType;

    private Long tamanho;


}
