package br.com.willbigas.algafood.api.v1.mapper;

import br.com.willbigas.algafood.api.v1.model.response.FormaPagamentoResponseDTO;
import br.com.willbigas.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoMapper {

    private final ModelMapper modelMapper;

    public FormaPagamentoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FormaPagamentoResponseDTO toResponseDTO(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento , FormaPagamentoResponseDTO.class);
    }

    public List<FormaPagamentoResponseDTO> toList(Collection<FormaPagamento> formaPagamentos) {
        return formaPagamentos.stream().map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

}
