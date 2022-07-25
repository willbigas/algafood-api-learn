package br.com.willbigas.algafood.api.model.response;

import br.com.willbigas.algafood.core.validation.Groups;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

@Getter
@Setter
@Relation(collectionRelation = "cidade")
public class CidadeResponseDTO extends RepresentationModel<CidadeResponseDTO> {

    private Long id;

    @NotBlank
    private String nome;

    @Valid
    @ConvertGroup(from = Default.class, to = Groups.EstadoId.class)
    @NotNull
    private EstadoResponseDTO estado;
}
