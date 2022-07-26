package br.com.willbigas.algafood.api.v1.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FormaPagamentoIDRequestDTO {

    @NotNull
    private Long id;

}
