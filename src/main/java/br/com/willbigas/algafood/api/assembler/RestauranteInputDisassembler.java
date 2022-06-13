package br.com.willbigas.algafood.api.assembler;

import br.com.willbigas.algafood.api.model.input.RestauranteInput;
import br.com.willbigas.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputDisassembler {

    private final ModelMapper modelMapper;

    public RestauranteInputDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Restaurante toDomainObject(RestauranteInput restauranteInput) {
        return modelMapper.map(restauranteInput, Restaurante.class);
    }

    public void copyToDomainObject(RestauranteInput restauranteInput , Restaurante restaurante) {
        modelMapper.map(restauranteInput , restaurante);
    }

}
