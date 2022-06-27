package br.com.willbigas.algafood.api.controller;

import br.com.willbigas.algafood.api.model.request.RestauranteRequestDTO;
import br.com.willbigas.algafood.api.mapper.RestauranteMapper;
import br.com.willbigas.algafood.api.model.response.RestauranteResponseDTO;
import br.com.willbigas.algafood.api.model.request.CozinhaIDRequestDTO;
import br.com.willbigas.algafood.core.validation.ValidacaoException;
import br.com.willbigas.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.willbigas.algafood.domain.exception.NegocioException;
import br.com.willbigas.algafood.domain.model.Restaurante;
import br.com.willbigas.algafood.domain.repository.RestauranteRepository;
import br.com.willbigas.algafood.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final RestauranteRepository restauranteRepository;
    private final RestauranteService restauranteService;
    private final SmartValidator validator;
    private final RestauranteMapper restauranteMapper;

    @Autowired
    public RestauranteController(RestauranteRepository restauranteRepository, RestauranteService restauranteService, SmartValidator validator, RestauranteMapper mapper) {
        this.restauranteRepository = restauranteRepository;
        this.restauranteService = restauranteService;
        this.validator = validator;
        this.restauranteMapper = mapper;
    }

    @GetMapping
    public List<RestauranteResponseDTO> listar() {
        return restauranteMapper.toCollectionModel(restauranteRepository.findAll());
    }

    @GetMapping("/{id}")
    public RestauranteResponseDTO buscar(@PathVariable Long id) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(id);
        return restauranteMapper.toModel(restaurante);
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
        return atualizar(id, toInputObject(restauranteAtual));
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

    private RestauranteRequestDTO toInputObject(Restaurante restaurante) {
        RestauranteRequestDTO input = new RestauranteRequestDTO();
        input.setNome(restaurante.getNome());
        input.setTaxaFrete(restaurante.getTaxaFrete());

        CozinhaIDRequestDTO cozinha = new CozinhaIDRequestDTO();
        cozinha.setId(restaurante.getCozinha().getId());
        input.setCozinha(cozinha);

        return input;
    }

}
