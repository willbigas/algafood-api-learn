package br.com.willbigas.algafood.core.modelmapper;

import br.com.willbigas.algafood.api.model.EnderecoModel;
import br.com.willbigas.algafood.api.model.RestauranteModel;
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

        modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
                .addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);

        modelMapper.createTypeMap(Endereco.class, EnderecoModel.class).
                addMapping(Endereco::getNomeEstadoDaCidade, EnderecoModel::setNomeEstadoDaCidade);

        return modelMapper;
    }
}
