package br.com.willbigas.algafood.api.v1.mapper;

import br.com.willbigas.algafood.api.v1.model.response.FotoProdutoResponseDTO;
import br.com.willbigas.algafood.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FotoProdutoMapper {

    private final ModelMapper modelMapper;

    public FotoProdutoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FotoProdutoResponseDTO toResponseDTO(FotoProduto fotoProduto) {
        return modelMapper.map(fotoProduto , FotoProdutoResponseDTO.class);
    }

    public List<FotoProdutoResponseDTO> toList(Collection<FotoProduto> fotos) {
        return fotos.stream().map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

}
