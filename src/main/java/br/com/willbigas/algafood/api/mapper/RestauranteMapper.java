package br.com.willbigas.algafood.api.mapper;

import br.com.willbigas.algafood.api.model.response.RestauranteResponseDTO;
import br.com.willbigas.algafood.api.model.request.RestauranteRequestDTO;
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

    public RestauranteResponseDTO toModel(Restaurante restaurante) {
        return modelMapper.map(restaurante , RestauranteResponseDTO.class);
    }

    public List<RestauranteResponseDTO> toCollectionModel(List<Restaurante> restaurantes) {
        return restaurantes.stream().map(this::toModel)
                .collect(Collectors.toList());
    }

    public Restaurante toRestaurante(RestauranteRequestDTO restauranteRequestDTO) {
        return modelMapper.map(restauranteRequestDTO, Restaurante.class);
    }

    public void copy(RestauranteRequestDTO restauranteRequestDTO, Restaurante restaurante) {

        if (restaurante.getEndereco() != null) {
            restaurante.getEndereco().setCidade(new Cidade());
        }

        modelMapper.map(restauranteRequestDTO, restaurante);
    }
}
