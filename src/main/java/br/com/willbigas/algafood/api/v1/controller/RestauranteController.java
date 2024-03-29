package br.com.willbigas.algafood.api.v1.controller;

import br.com.willbigas.algafood.api.v1.model.request.RestauranteRequestDTO;
import br.com.willbigas.algafood.api.v1.mapper.RestauranteMapper;
import br.com.willbigas.algafood.api.v1.model.response.RestauranteResponseDTO;
import br.com.willbigas.algafood.api.v1.model.request.CozinhaIDRequestDTO;
import br.com.willbigas.algafood.core.validation.ValidacaoException;
import br.com.willbigas.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.willbigas.algafood.domain.exception.NegocioException;
import br.com.willbigas.algafood.domain.model.Restaurante;
import br.com.willbigas.algafood.domain.repository.RestauranteRepositoryCustomized;
import br.com.willbigas.algafood.domain.filter.RestauranteFilter;
import br.com.willbigas.algafood.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

    private final RestauranteRepositoryCustomized restauranteRepository;
    private final RestauranteService restauranteService;
    private final SmartValidator validator;
    private final RestauranteMapper restauranteMapper;

    @Autowired
    public RestauranteController(RestauranteRepositoryCustomized restauranteRepository, RestauranteService restauranteService, SmartValidator validator, RestauranteMapper mapper) {
        this.restauranteRepository = restauranteRepository;
        this.restauranteService = restauranteService;
        this.validator = validator;
        this.restauranteMapper = mapper;
    }

    @GetMapping
    public List<RestauranteResponseDTO> listar() {
        return restauranteMapper.toCollectionModel(restauranteRepository.findAll());
    }

    @GetMapping("/page")
    public Page<RestauranteResponseDTO> listar(Pageable pageable) {
        return restauranteMapper.toPageDTO(restauranteRepository.findAll(pageable));
    }

    @GetMapping("/{id}")
    public RestauranteResponseDTO buscar(@PathVariable Long id) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(id);
        return restauranteMapper.toModel(restaurante);
    }


    /**
     * Implementação extremamente Genérica Utilizando Criteria e FilterDTO e Pageable (Paginação e Multi Ordenação)
     * URL -> /restaurantes/customizado?page=0&size=3&sort=nome,desc&sort=ativo,desc&nome=Tuk
     * @param filter
     * @param pageable
     * @return
     */
    @GetMapping("/customizado")
    public Page<RestauranteResponseDTO> buscarCustomizado(RestauranteFilter filter, Pageable pageable) {
        return restauranteMapper.toPageDTO(restauranteRepository.findWithPageAndSortCustomize(filter, pageable));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteResponseDTO adicionar(@RequestBody @Valid RestauranteRequestDTO restauranteRequestDTO) {
        try {

            Restaurante restaurante = restauranteMapper.toRestaurante(restauranteRequestDTO);
            return restauranteMapper.toModel(restauranteService.salvar(restaurante));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public RestauranteResponseDTO atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteRequestDTO restauranteRequestDTO) {

        try {
            Restaurante restauranteAtual = restauranteService.buscarOuFalhar(id);
            restauranteMapper.copy(restauranteRequestDTO, restauranteAtual);

            return restauranteMapper.toModel(restauranteService.salvar(restauranteAtual));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }

    }

    @PatchMapping("/{id}")
    public RestauranteResponseDTO atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos, HttpServletRequest request) {
        Restaurante restauranteAtual = restauranteService.buscarOuFalhar(id);
        merge(campos, restauranteAtual, request);
        validate(restauranteAtual, "restaurante");
        return atualizar(id, toRequestDTO(restauranteAtual));
    }

    private void validate(Restaurante restaurante, String objectName) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
        validator.validate(restaurante, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidacaoException(bindingResult);
        }
    }

    private void merge(@RequestBody Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request) {

        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

            dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
                field.setAccessible(true);
                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
                ReflectionUtils.setField(field, restauranteDestino, novoValor);
            });
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        restauranteService.excluir(id);
    }

    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long id) {
        restauranteService.ativar(id);
    }

    @DeleteMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long id) {
        restauranteService.inativar(id);
    }


    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> ids) {
        restauranteService.ativar(ids);
    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> ids) {
        restauranteService.inativar(ids);
    }


    @PutMapping("/{id}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrir(@PathVariable Long id) {
        restauranteService.abrir(id);
    }

    @PutMapping("/{id}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechar(@PathVariable Long id) {
        restauranteService.fechar(id);
    }

    private RestauranteRequestDTO toRequestDTO(Restaurante restaurante) {
        RestauranteRequestDTO requestDTO = new RestauranteRequestDTO();
        requestDTO.setNome(restaurante.getNome());
        requestDTO.setTaxaFrete(restaurante.getTaxaFrete());

        CozinhaIDRequestDTO cozinha = new CozinhaIDRequestDTO();
        cozinha.setId(restaurante.getCozinha().getId());
        requestDTO.setCozinha(cozinha);

        return requestDTO;
    }

}
