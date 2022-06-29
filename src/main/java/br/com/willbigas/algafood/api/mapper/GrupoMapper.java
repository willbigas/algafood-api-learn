package br.com.willbigas.algafood.api.mapper;

import br.com.willbigas.algafood.api.model.response.GrupoResponseDTO;
import br.com.willbigas.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GrupoMapper {

    private final ModelMapper modelMapper;

    public GrupoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public GrupoResponseDTO toResponseDTO(Grupo grupo) {
        return modelMapper.map(grupo, GrupoResponseDTO.class);
    }

    public List<GrupoResponseDTO> toList(Collection<Grupo> grupos) {
        return grupos.stream().map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}
