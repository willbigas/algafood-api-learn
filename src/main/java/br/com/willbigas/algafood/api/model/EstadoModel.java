package br.com.willbigas.algafood.api.model;

import br.com.willbigas.algafood.core.validation.Groups;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class EstadoModel {

    @NotNull(groups = Groups.EstadoId.class)
    private Long id;

    private String nome;
}
