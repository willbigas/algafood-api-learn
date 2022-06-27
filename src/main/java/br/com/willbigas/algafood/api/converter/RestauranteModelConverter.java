package br.com.willbigas.algafood.api.converter;

import br.com.willbigas.algafood.api.model.RestauranteModel;
import br.com.willbigas.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteModelConverter {


    private final ModelMapper modelMapper;

    public RestauranteModelConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RestauranteModel toModel(Restaurante restaurante) {
        return modelMapper.map(restaurante , RestauranteModel.class);
    }

    public List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes) {
        return restaurantes.stream().map(this::toModel)
                .collect(Collectors.toList());
    }
}
