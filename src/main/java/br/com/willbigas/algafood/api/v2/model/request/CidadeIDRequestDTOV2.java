package br.com.willbigas.algafood.api.v2.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeIDRequestDTOV2 {

    @NotNull
    private Long id;
}
