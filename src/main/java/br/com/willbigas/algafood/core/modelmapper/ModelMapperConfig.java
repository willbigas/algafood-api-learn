package br.com.willbigas.algafood.core.modelmapper;

import br.com.willbigas.algafood.api.v1.model.request.ItemPedidoRequestDTO;
import br.com.willbigas.algafood.api.v1.model.response.EnderecoResponseDTO;
import br.com.willbigas.algafood.api.v1.model.response.RestauranteResponseDTO;
import br.com.willbigas.algafood.domain.model.Endereco;
import br.com.willbigas.algafood.domain.model.ItemPedido;
import br.com.willbigas.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(Restaurante.class, RestauranteResponseDTO.class)
                .addMapping(Restaurante::getTaxaFrete, RestauranteResponseDTO::setPrecoFrete);

        modelMapper.createTypeMap(Endereco.class, EnderecoResponseDTO.class).
                addMapping(Endereco::getNomeEstadoDaCidade, EnderecoResponseDTO::setNomeEstadoDaCidade);


        modelMapper.createTypeMap(ItemPedidoRequestDTO.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));


        return modelMapper;
    }
}
