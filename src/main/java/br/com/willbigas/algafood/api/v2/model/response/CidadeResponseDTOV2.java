package br.com.willbigas.algafood.api.v2.model.response;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CidadeResponseDTOV2 {

    private Long id;

    @NotBlank
    private String nome;

}
