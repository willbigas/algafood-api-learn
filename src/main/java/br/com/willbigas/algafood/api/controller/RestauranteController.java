package br.com.willbigas.algafood.api.controller;

import br.com.willbigas.algafood.api.model.CozinhaModel;
import br.com.willbigas.algafood.api.model.RestauranteModel;
import br.com.willbigas.algafood.api.model.input.CozinhaIdInput;
import br.com.willbigas.algafood.api.model.input.RestauranteInput;
import br.com.willbigas.algafood.core.validation.ValidacaoException;
import br.com.willbigas.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.willbigas.algafood.domain.exception.NegocioException;
import br.com.willbigas.algafood.domain.model.Cozinha;
import br.com.willbigas.algafood.domain.model.Restaurante;
import br.com.willbigas.algafood.domain.repository.RestauranteRepository;
import br.com.willbigas.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private SmartValidator validator;

    @GetMapping
    public List<RestauranteModel> listar() {
        return toCollectionModel(restauranteRepository.findAll());
    }

    @GetMapping("/{id}")
    public RestauranteModel buscar(@PathVariable Long id) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(id);
        return toModel(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {

            Restaurante restaurante = toDomaingObject(restauranteInput);
            return toModel(cadastroRestauranteService.salvar(restaurante));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public RestauranteModel atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInput restauranteInput) {

        try {
            Restaurante restaurante = toDomaingObject(restauranteInput);

            Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(id);
            BeanUtils.copyProperties(restaurante, restauranteAtual,
                    "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
            return toModel(cadastroRestauranteService.salvar(restauranteAtual));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }

    }

    @PatchMapping("/{id}")
    public RestauranteModel atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos, HttpServletRequest request) {
        Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(id);
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
        cadastroRestauranteService.excluir(id);
    }

    private RestauranteModel toModel(Restaurante restaurante) {
        CozinhaModel cozinhaModel = new CozinhaModel();
        cozinhaModel.setId(restaurante.getCozinha().getId());
        cozinhaModel.setNome(restaurante.getCozinha().getNome());

        RestauranteModel model = new RestauranteModel();
        model.setId(restaurante.getId());
        model.setNome(restaurante.getNome());
        model.setTaxaFrete(restaurante.getTaxaFrete());
        model.setCozinha(cozinhaModel);
        return model;
    }

    private List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes) {
        return restaurantes.stream().map(this::toModel)
                .collect(Collectors.toList());
    }

    private Restaurante toDomaingObject(RestauranteInput restauranteInput) {
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(restauranteInput.getNome());
        restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());

        Cozinha cozinha = new Cozinha();
        cozinha.setId(restauranteInput.getCozinha().getId());
        restaurante.setCozinha(cozinha);

        return restaurante;
    }

    private RestauranteInput toInputObject(Restaurante restaurante) {
        RestauranteInput input = new RestauranteInput();
        input.setNome(restaurante.getNome());
        input.setTaxaFrete(restaurante.getTaxaFrete());

        CozinhaIdInput cozinha = new CozinhaIdInput();
        cozinha.setId(restaurante.getCozinha().getId());
        input.setCozinha(cozinha);

        return input;
    }
}
