package br.com.willbigas.algafood.api.assembler;

import br.com.willbigas.algafood.api.model.FormaPagamentoModel;
import br.com.willbigas.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoConverter {

    private final ModelMapper modelMapper;

    public FormaPagamentoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento , FormaPagamentoModel.class);
    }

    public List<FormaPagamentoModel> toList(Collection<FormaPagamento> formaPagamentos) {
        return formaPagamentos.stream().map(this::toModel)
                .collect(Collectors.toList());
    }

}
