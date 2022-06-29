package br.com.willbigas.algafood.api.mapper;

import br.com.willbigas.algafood.api.model.response.PermissaoResponseDTO;
import br.com.willbigas.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissaoMapper {

    private final ModelMapper modelMapper;

    public PermissaoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PermissaoResponseDTO toResponseDTO(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoResponseDTO.class);
    }

    public List<PermissaoResponseDTO> toList(Collection<Permissao> permissoes) {
        return permissoes.stream().map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}
