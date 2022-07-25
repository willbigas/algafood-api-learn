package br.com.willbigas.algafood.api.mapper;

import br.com.willbigas.algafood.api.model.response.CidadeResponseDTO;
import br.com.willbigas.algafood.api.model.response.FormaPagamentoResponseDTO;
import br.com.willbigas.algafood.domain.model.Cidade;
import br.com.willbigas.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CidadeMapper {

    private final ModelMapper modelMapper;

    public CidadeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CidadeResponseDTO toResponseDTO(Cidade cidade) {
        return modelMapper.map(cidade , CidadeResponseDTO.class);
    }

    public List<CidadeResponseDTO> toList(Collection<Cidade> cidades) {
        return cidades.stream().map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

}
