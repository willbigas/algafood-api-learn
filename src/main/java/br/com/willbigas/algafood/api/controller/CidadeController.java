package br.com.willbigas.algafood.api.controller;

import br.com.willbigas.algafood.api.controller.openapi.CidadeControllerOpenAPI;
import br.com.willbigas.algafood.api.helper.ResourceUriHelper;
import br.com.willbigas.algafood.api.mapper.CidadeMapper;
import br.com.willbigas.algafood.api.model.response.CidadeResponseDTO;
import br.com.willbigas.algafood.domain.exception.CidadeNaoEncontradaException;
import br.com.willbigas.algafood.domain.exception.NegocioException;
import br.com.willbigas.algafood.domain.model.Cidade;
import br.com.willbigas.algafood.domain.service.CidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenAPI {

    private final CidadeService cidadeService;

    private final CidadeMapper cidadeMapper;

    public CidadeController(CidadeService cidadeService, CidadeMapper cidadeMapper) {
        this.cidadeService = cidadeService;
        this.cidadeMapper = cidadeMapper;
    }

    @GetMapping
    public ResponseEntity<List<CidadeResponseDTO>> listar() {

        List<Cidade> cidades = cidadeService.findAll();
        List<CidadeResponseDTO> responseDTOS = cidadeMapper.toList(cidades);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
//                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic()) // caches locais e compartilhados
//                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate()) // cache somente local
//                .cacheControl(CacheControl.noCache()) // Sempre armazena o cache em stale e sempre precisa ser revalidado.
//                .cacheControl(CacheControl.noStore()) // Não cacheavel
                .body(responseDTOS);
    }

    @GetMapping("/listar-com-hateoas")
    public CollectionModel<CidadeResponseDTO> listarHateOAS() {

        List<Cidade> cidades = cidadeService.findAll();
        List<CidadeResponseDTO> responseDTOS = cidadeMapper.toList(cidades);

        responseDTOS.forEach(this::createLinkFindByIDHateOAS);

        CollectionModel<CidadeResponseDTO> collectionModel = CollectionModel.of(responseDTOS);

        collectionModel.add(linkTo(CidadeController.class).withSelfRel());

        return collectionModel;
    }



    @GetMapping("/{id}")
    public CidadeResponseDTO buscar(@PathVariable Long id) {
        Cidade cidade = cidadeService.buscarOuFalhar(id);
        CidadeResponseDTO cidadeResponseDTO = cidadeMapper.toResponseDTO(cidade);
        createLinkFindByIDHateOAS(cidadeResponseDTO);
        return cidadeResponseDTO;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeResponseDTO adicionar(@RequestBody @Valid Cidade cidade) {
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


    private void createLinkFindByIDHateOAS(CidadeResponseDTO cidadeResponseDTO) {

        cidadeResponseDTO.add(linkTo(methodOn(CidadeController.class)
                .buscar(cidadeResponseDTO.getId()))
                .withSelfRel());

        //        cidadeResponseDTO.add(linkTo(CidadeController.class)
        //                .slash(cidadeResponseDTO.getId())
        //                .withSelfRel());

        //        cidadeResponseDTO.add(Link.of("http://localhost:8080/cidades/1" , IanaLinkRelations.SELF));

        //        cidadeResponseDTO.add(Link.of("http://localhost:8080/cidades/1"));


        cidadeResponseDTO.add(linkTo(methodOn(CidadeController.class)
                .listar())
                .withRel("cidades"));


        //
        //        cidadeResponseDTO.add(linkTo(CidadeController.class)
        //                .withRel("cidades"));

        //        cidadeResponseDTO.add(Link.of("http://localhost:8080/cidades", IanaLinkRelations.COLLECTION));

        //        cidadeResponseDTO.add(Link.of("http://localhost:8080/cidades", "cidades"));

        cidadeResponseDTO.getEstado().add(linkTo(methodOn(EstadoController.class)
                .buscar(cidadeResponseDTO.getEstado().getId()))
                .withSelfRel());

        //
        //        cidadeResponseDTO.getEstado().add(linkTo(EstadoController.class)
        //                .slash(cidadeResponseDTO.getEstado().getId())
        //                .withSelfRel());

        //        cidadeResponseDTO.getEstado().add(Link.of("http://localhost:8080/estados/1"));
    }

}
