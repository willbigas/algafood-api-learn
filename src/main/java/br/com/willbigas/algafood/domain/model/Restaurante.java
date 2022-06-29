package br.com.willbigas.algafood.domain.model;

import br.com.willbigas.algafood.core.validation.ValorZeroIncluiDescricao;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@ValorZeroIncluiDescricao(valorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    @ManyToOne
    @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;

    @Embedded
    private Endereco endereco;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    @OneToMany(mappedBy = "restaurante" , fetch = FetchType.LAZY)
    private Set<Produto> produtos;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private Set<FormaPagamento> formasPagamento = new HashSet<>();

    private Boolean ativo = Boolean.TRUE;

    private Boolean aberto = Boolean.TRUE;

    public void ativar() {
        setAtivo(true);
    }

    public void inativar() {
        setAtivo(false);
    }

    public void adicionarFormaPagamento(FormaPagamento formaPagamento) {
        this.getFormasPagamento().add(formaPagamento);
    }

    public void removerFormaPagamento(FormaPagamento formaPagamento) {
        this.getFormasPagamento().remove(formaPagamento);
    }

    public void adicionarProduto(Produto produto) {
        this.getProdutos().add(produto);
    }

    public void removerProduto(Produto produto) {
        this.getProdutos().remove(produto);
    }

    public void abrir() {
       setAberto(true);
    }

    public void fechar() {
        setAberto(false);
    }

}
