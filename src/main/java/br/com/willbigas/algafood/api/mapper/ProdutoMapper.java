package br.com.willbigas.algafood.api.mapper;

import br.com.willbigas.algafood.api.model.response.ProdutoResponseDTO;
import br.com.willbigas.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoMapper {

    private final ModelMapper modelMapper;

    public ProdutoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProdutoResponseDTO toModel(Produto produto) {
        return modelMapper.map(produto , ProdutoResponseDTO.class);
    }

    public List<ProdutoResponseDTO> toList(Collection<Produto> formaPagamentos) {
        return formaPagamentos.stream().map(this::toModel)
                .collect(Collectors.toList());
    }

}
