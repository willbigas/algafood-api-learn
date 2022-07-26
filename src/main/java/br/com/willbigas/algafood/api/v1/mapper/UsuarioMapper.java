package br.com.willbigas.algafood.api.v1.mapper;

import br.com.willbigas.algafood.api.v1.model.request.UsuarioRequestDTO;
import br.com.willbigas.algafood.api.v1.model.response.UsuarioResponseDTO;
import br.com.willbigas.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {

    private final ModelMapper modelMapper;

    public UsuarioMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return modelMapper.map(usuario , UsuarioResponseDTO.class);
    }

    public List<UsuarioResponseDTO> toList(Collection<Usuario> usuarios) {
        return usuarios.stream().map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Usuario toUsuario(UsuarioRequestDTO usuarioRequestDTO) {
        return modelMapper.map(usuarioRequestDTO, Usuario.class);
    }

    public void copy(UsuarioRequestDTO usuarioRequestDTO, Usuario usuario) {
        modelMapper.map(usuarioRequestDTO, usuario);
    }
}
