package br.com.willbigas.algafood.core.modelmapper;

import br.com.willbigas.algafood.api.model.response.EnderecoResponseDTO;
import br.com.willbigas.algafood.api.model.response.RestauranteResponseDTO;
import br.com.willbigas.algafood.domain.model.Endereco;
import br.com.willbigas.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(Restaurante.class, RestauranteResponseDTO.class)
                .addMapping(Restaurante::getTaxaFrete, RestauranteResponseDTO::setPrecoFrete);

        modelMapper.createTypeMap(Endereco.class, EnderecoResponseDTO.class).
                addMapping(Endereco::getNomeEstadoDaCidade, EnderecoResponseDTO::setNomeEstadoDaCidade);

        return modelMapper;
    }
}
