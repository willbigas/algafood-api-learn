package br.com.willbigas.algafood.api.v1.controller;

import br.com.willbigas.algafood.api.v1.mapper.GrupoMapper;
import br.com.willbigas.algafood.api.v1.model.response.GrupoResponseDTO;
import br.com.willbigas.algafood.domain.model.Usuario;
import br.com.willbigas.algafood.domain.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/usuarios/{idUsuario}/grupos")
public class UsuarioGrupoController {

    private final UsuarioService usuarioService;
    private final GrupoMapper grupoMapper;

    public UsuarioGrupoController(UsuarioService usuarioService, GrupoMapper grupoMapper) {
        this.usuarioService = usuarioService;
        this.grupoMapper = grupoMapper;
    }

    @GetMapping
    public List<GrupoResponseDTO> listar(@PathVariable Long idUsuario) {
        Usuario usuario = usuarioService.buscarOuFalhar(idUsuario);
        return grupoMapper.toList(usuario.getGrupos());
    }

    @PutMapping("/{idGrupo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long idUsuario, @PathVariable Long idGrupo) {
        usuarioService.associarGrupo(idUsuario, idGrupo);
    }

    @DeleteMapping("/{idGrupo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long idUsuario, @PathVariable Long idGrupo) {
        usuarioService.desassociarGrupo(idUsuario, idGrupo);
    }


}
