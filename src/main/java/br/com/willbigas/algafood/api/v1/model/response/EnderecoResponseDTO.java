package br.com.willbigas.algafood.api.v1.model.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoResponseDTO {

    private String cep;

    private String logradouro;

    private String numero;

    private String complemento;

    private String bairro;

    private CidadeResumidaResponseDTO cidade;

    public void setNomeEstadoDaCidade(String nome) {
        cidade.setEstado(nome);
    }


}
