package br.com.willbigas.algafood.api.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequestDTO {

    private Long id;
    private String nome;
    private String email;
    private String senha;
}
