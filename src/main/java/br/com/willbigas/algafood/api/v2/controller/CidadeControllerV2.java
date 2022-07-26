package br.com.willbigas.algafood.api.v2.controller;

import br.com.willbigas.algafood.api.v1.helper.ResourceUriHelper;
import br.com.willbigas.algafood.api.v2.mapper.CidadeMapperV2;
import br.com.willbigas.algafood.api.v2.model.response.CidadeResponseDTOV2;
import br.com.willbigas.algafood.domain.exception.CidadeNaoEncontradaException;
import br.com.willbigas.algafood.domain.exception.NegocioException;
import br.com.willbigas.algafood.domain.model.Cidade;
import br.com.willbigas.algafood.domain.service.CidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path = "/v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeControllerV2 {

    private final CidadeService cidadeService;

    private final CidadeMapperV2 cidadeMapper;

    public CidadeControllerV2(CidadeService cidadeService, CidadeMapperV2 cidadeMapper) {
        this.cidadeService = cidadeService;
        this.cidadeMapper = cidadeMapper;
    }

    @GetMapping
    public ResponseEntity<List<CidadeResponseDTOV2>> listar() {

        List<Cidade> cidades = cidadeService.findAll();
        List<CidadeResponseDTOV2> responseDTOS = cidadeMapper.toList(cidades);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(responseDTOS);
    }

    @GetMapping("/{id}")
    public CidadeResponseDTOV2 buscar(@PathVariable Long id) {
        Cidade cidade = cidadeService.buscarOuFalhar(id);
        return cidadeMapper.toResponseDTO(cidade);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeResponseDTOV2 adicionar(@RequestBody @Valid Cidade cidade) {
        try {
            Cidade cidadeSalva = cidadeService.salvar(cidade);
            ResourceUriHelper.addUriInResponseHeader(cidadeSalva.getId());
            return cidadeMapper.toResponseDTO(cidadeSalva);
        } catch (CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public Cidade atualizar(@PathVariable Long id, @RequestBody @Valid Cidade cidade) {
        Cidade cidadeAtual = cidadeService.buscarOuFalhar(id);
        BeanUtils.copyProperties(cidade, cidadeAtual, "id");
        try {
            return cidadeService.salvar(cidadeAtual);
        } catch (CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cidadeService.excluir(id);
    }

}
