package br.com.willbigas.algafood.api.mapper;

import br.com.willbigas.algafood.api.model.request.ProdutoRequestDTO;
import br.com.willbigas.algafood.api.model.request.ProdutoRequestSimplificadoDTO;
import br.com.willbigas.algafood.api.model.response.ProdutoResponseDTO;
import br.com.willbigas.algafood.api.model.response.ProdutoResumidoResponseDTO;
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

    public ProdutoResponseDTO toResponseDTO(Produto produto) {
        return modelMapper.map(produto , ProdutoResponseDTO.class);
    }

    public ProdutoResponseDTO toResponseDTO(ProdutoRequestDTO produtoRequestDTO) {
        return modelMapper.map(produtoRequestDTO , ProdutoResponseDTO.class);
    }

    public Produto toProduto(ProdutoRequestDTO produtoRequestDTO) {
        return modelMapper.map(produtoRequestDTO, Produto.class);
    }

    public Produto toProduto(ProdutoRequestSimplificadoDTO produtoRequestSimplificadoDTO) {
        return modelMapper.map(produtoRequestSimplificadoDTO, Produto.class);
    }

    public List<ProdutoResponseDTO> toList(Collection<Produto> formaPagamentos) {
        return formaPagamentos.stream().map(this::toResponseDTO)
                .collect(Collectors.toList());
    }


    public ProdutoResumidoResponseDTO toProdutoResumido(Produto produto) {
        return modelMapper.map(produto , ProdutoResumidoResponseDTO.class);
    }

    public List<ProdutoResumidoResponseDTO> toListProdutoResumido(Collection<Produto> produtos) {
        return produtos.stream().map(this::toProdutoResumido)
                .collect(Collectors.toList());
    }

}
