package br.com.willbigas.algafood.api.mapper;

import br.com.willbigas.algafood.api.model.RestauranteModel;
import br.com.willbigas.algafood.api.model.input.RestauranteInput;
import br.com.willbigas.algafood.domain.model.Cidade;
import br.com.willbigas.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteMapper {


    private final ModelMapper modelMapper;

    public RestauranteMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RestauranteModel toModel(Restaurante restaurante) {
        return modelMapper.map(restaurante , RestauranteModel.class);
    }

    public List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes) {
        return restaurantes.stream().map(this::toModel)
                .collect(Collectors.toList());
    }

    public Restaurante toRestaurante(RestauranteInput restauranteInput) {
        return modelMapper.map(restauranteInput, Restaurante.class);
    }

    public void copy(RestauranteInput restauranteInput , Restaurante restaurante) {

        if (restaurante.getEndereco() != null) {
            restaurante.getEndereco().setCidade(new Cidade());
        }

        modelMapper.map(restauranteInput , restaurante);
    }
}
