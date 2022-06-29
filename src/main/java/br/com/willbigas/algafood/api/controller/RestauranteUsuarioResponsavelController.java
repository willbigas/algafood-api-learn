package br.com.willbigas.algafood.api.controller;

import br.com.willbigas.algafood.api.mapper.UsuarioMapper;
import br.com.willbigas.algafood.api.model.response.UsuarioResponseDTO;
import br.com.willbigas.algafood.domain.model.Restaurante;
import br.com.willbigas.algafood.domain.service.RestauranteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes/{idRestaurante}/responsaveis")
public class RestauranteUsuarioResponsavelController {

    private final RestauranteService restauranteService;
    private final UsuarioMapper usuarioMapper;

    public RestauranteUsuarioResponsavelController(RestauranteService restauranteService, UsuarioMapper usuarioMapper) {
        this.restauranteService = restauranteService;
        this.usuarioMapper = usuarioMapper;
    }

    @GetMapping
    public List<UsuarioResponseDTO> listar(@PathVariable Long idRestaurante) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(idRestaurante);

        return usuarioMapper.toList(restaurante.getResponsaveis());
    }

    @DeleteMapping("/{idUsuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long idRestaurante, @PathVariable Long idUsuario) {
        restauranteService.desassociarResponsavel(idRestaurante, idUsuario);
    }

    @PutMapping("/{idUsuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long idRestaurante, @PathVariable Long idUsuario) {
        restauranteService.associarResponsavel(idRestaurante, idUsuario);
    }


}
