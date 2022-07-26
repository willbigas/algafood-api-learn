package br.com.willbigas.algafood.api.v1.controller;

import br.com.willbigas.algafood.api.v1.mapper.UsuarioMapper;
import br.com.willbigas.algafood.api.v1.model.request.UsuarioRequestDTO;
import br.com.willbigas.algafood.api.v1.model.response.UsuarioResponseDTO;
import br.com.willbigas.algafood.domain.exception.NegocioException;
import br.com.willbigas.algafood.domain.exception.UsuarioNaoEncontradoException;
import br.com.willbigas.algafood.domain.model.Usuario;
import br.com.willbigas.algafood.domain.service.UsuarioService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    public UsuarioController(UsuarioService usuarioService, UsuarioMapper usuarioMapper) {
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
    }

    @GetMapping
    public List<UsuarioResponseDTO> listar() {
        return usuarioMapper.toList(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public UsuarioResponseDTO buscar(@PathVariable Long id) {
        return usuarioMapper.toResponseDTO(usuarioService.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponseDTO adicionar(@RequestBody @Valid UsuarioRequestDTO usuarioRequestDTO) {
        try {
            Usuario usuario = usuarioMapper.toUsuario(usuarioRequestDTO);
            return usuarioMapper.toResponseDTO(usuarioService.salvar(usuario));
        } catch (UsuarioNaoEncontradoException e) {
            throw new NegocioException(e.getMessage() , e);
        }
    }

    @PutMapping("/{id}")
    public UsuarioResponseDTO atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioRequestDTO usuarioRequestDTO) {
        Usuario usuarioAtual = usuarioService.buscarOuFalhar(id);
        BeanUtils.copyProperties(usuarioRequestDTO, usuarioAtual, "id");
        try {
            return usuarioMapper.toResponseDTO(usuarioService.salvar(usuarioAtual));
        } catch (UsuarioNaoEncontradoException e) {
            throw new NegocioException(e.getMessage() , e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        usuarioService.excluir(id);
    }

}
