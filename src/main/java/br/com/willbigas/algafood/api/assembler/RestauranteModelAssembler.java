package br.com.willbigas.algafood.api.assembler;

import br.com.willbigas.algafood.api.model.CozinhaModel;
import br.com.willbigas.algafood.api.model.RestauranteModel;
import br.com.willbigas.algafood.domain.model.Restaurante;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteModelAssembler {


    public RestauranteModel toModel(Restaurante restaurante) {
        CozinhaModel cozinhaModel = new CozinhaModel();
        cozinhaModel.setId(restaurante.getCozinha().getId());
        cozinhaModel.setNome(restaurante.getCozinha().getNome());

        RestauranteModel model = new RestauranteModel();
        model.setId(restaurante.getId());
        model.setNome(restaurante.getNome());
        model.setTaxaFrete(restaurante.getTaxaFrete());
        model.setCozinha(cozinhaModel);
        return model;
    }

    public List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes) {
        return restaurantes.stream().map(this::toModel)
                .collect(Collectors.toList());
    }
}
