package br.com.willbigas.algafood.api.controller;

import br.com.willbigas.algafood.api.mapper.PermissaoMapper;
import br.com.willbigas.algafood.api.model.response.PermissaoResponseDTO;
import br.com.willbigas.algafood.domain.model.Grupo;
import br.com.willbigas.algafood.domain.service.GrupoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/grupos/{idGrupo}/permissoes")
public class GrupoPermissaoController {

    private final GrupoService grupoService;
    private final PermissaoMapper permissaoMapper;

    public GrupoPermissaoController(GrupoService grupoService, PermissaoMapper permissaoMapper) {
        this.grupoService = grupoService;
        this.permissaoMapper = permissaoMapper;
    }

    @GetMapping
    public List<PermissaoResponseDTO> listar(@PathVariable Long idGrupo) {
        Grupo grupo = grupoService.buscarOuFalhar(idGrupo);
        return permissaoMapper.toList(grupo.getPermissoes());
    }

    @DeleteMapping("/{idPermissao}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long idGrupo, @PathVariable Long idPermissao) {
        grupoService.desassociarPermissao(idGrupo, idPermissao);
    }

    @PutMapping("/{idPermissao}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long idGrupo, @PathVariable Long idPermissao) {
        grupoService.associarPermissao(idGrupo, idPermissao);
    }

}
