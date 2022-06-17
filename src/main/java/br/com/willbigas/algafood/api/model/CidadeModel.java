package br.com.willbigas.algafood.api.model;

import br.com.willbigas.algafood.core.validation.Groups;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

@Getter
@Setter
public class CidadeModel {

    private Long id;

    @NotBlank
    private String nome;

    @Valid
    @ConvertGroup(from = Default.class, to = Groups.EstadoId.class)
    @NotNull
    private EstadoModel estado;
}
