package br.com.willbigas.algafood.api.model.response;

import br.com.willbigas.algafood.core.validation.Groups;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class EstadoResponseDTO {

    @NotNull(groups = Groups.EstadoId.class)
    private Long id;

    private String nome;
}
