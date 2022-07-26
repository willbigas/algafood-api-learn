package br.com.willbigas.algafood.api.v2.mapper;

import br.com.willbigas.algafood.api.v1.model.response.CidadeResponseDTO;
import br.com.willbigas.algafood.api.v2.model.response.CidadeResponseDTOV2;
import br.com.willbigas.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CidadeMapperV2 {

    private final ModelMapper modelMapper;

    public CidadeMapperV2(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CidadeResponseDTOV2 toResponseDTO(Cidade cidade) {
        return modelMapper.map(cidade , CidadeResponseDTOV2.class);
    }

    public List<CidadeResponseDTOV2> toList(Collection<Cidade> cidades) {
        return cidades.stream().map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

}
