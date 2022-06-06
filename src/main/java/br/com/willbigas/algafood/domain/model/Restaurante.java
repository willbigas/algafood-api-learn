package br.com.willbigas.algafood.domain.model;

import br.com.willbigas.algafood.core.validation.Groups;
import br.com.willbigas.algafood.core.validation.Multiplo;
import br.com.willbigas.algafood.core.validation.TaxaFrete;
import br.com.willbigas.algafood.core.validation.ValorZeroIncluiDescricao;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@ValorZeroIncluiDescricao(valorField = "taxaFrete" , descricaoField = "nome" , descricaoObrigatoria = "Frete Grátis")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @TaxaFrete
    @Column(name = "taxa_frete" , nullable = false)
    @Multiplo(numero = 5)
    private BigDecimal taxaFrete;

    @Valid
    @ConvertGroup(from = Default.class , to = Groups.CozinhaId.class)
    @NotNull
    @ManyToOne
    @JoinColumn(name = "cozinha_id" , nullable = false)
    private Cozinha cozinha;

    @Embedded
    private Endereco endereco;

    @CreationTimestamp
    @Column(nullable = false , columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(nullable = false , columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "restaurante_forma_pagamento" ,
                joinColumns = @JoinColumn(name = "restaurante_id"),
                inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private List<FormaPagamento> formasPagamento = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getTaxaFrete() {
        return taxaFrete;
    }

    public void setTaxaFrete(BigDecimal taxaFrete) {
        this.taxaFrete = taxaFrete;
    }

    public Cozinha getCozinha() {
        return cozinha;
    }

    public void setCozinha(Cozinha cozinha) {
        this.cozinha = cozinha;
    }
}
