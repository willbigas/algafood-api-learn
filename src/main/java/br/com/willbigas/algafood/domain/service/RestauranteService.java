package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.domain.exception.CozinhaNaoEncontradaException;
import br.com.willbigas.algafood.domain.exception.EntidadeEmUsoException;
import br.com.willbigas.algafood.domain.exception.RestauranteNaoEncontradoException;
import br.com.willbigas.algafood.domain.model.Cidade;
import br.com.willbigas.algafood.domain.model.Cozinha;
import br.com.willbigas.algafood.domain.model.FormaPagamento;
import br.com.willbigas.algafood.domain.model.Restaurante;
import br.com.willbigas.algafood.domain.repository.CozinhaRepository;
import br.com.willbigas.algafood.domain.repository.RestauranteRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final CozinhaRepository cozinhaRepository;
    private final CidadeService cidadeService;
    private final FormaPagamentoService formaPagamentoService;

    private static final String MSG_RESTAURANTE_NAO_ENCONTRADO
            = "Não existe um cadastro de restaurante com código %d";

    public RestauranteService(RestauranteRepository restauranteRepository, CozinhaRepository cozinhaRepository , CidadeService cidadeService, FormaPagamentoService formaPagamentoService) {
        this.restauranteRepository = restauranteRepository;
        this.cozinhaRepository = cozinhaRepository;
        this.cidadeService = cidadeService;
        this.formaPagamentoService = formaPagamentoService;
    }


    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Long idCozinha = restaurante.getCozinha().getId();
        Long idCidade = restaurante.getEndereco().getCidade().getId();

        Cozinha cozinha = cozinhaRepository.findById(idCozinha)
                .orElseThrow(() -> new CozinhaNaoEncontradaException(String.format("Não existe cadastro de cozinha com o código %d", idCozinha)));

        Cidade cidade = cidadeService.buscarOuFalhar(idCidade);

        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);
        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public void excluir(Long restauranteID) {
        try {
            restauranteRepository.deleteById(restauranteID);
            restauranteRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new RestauranteNaoEncontradoException(String.format("Não existe um cadastro de cozinha com código %d ", restauranteID));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Cozinha de código %d não pode ser removida, pois está em uso", restauranteID));
        }
    }

    @Transactional
    public void ativar(Long idRestaurante) {
        buscarOuFalhar(idRestaurante).ativar();
    }

    @Transactional
    public void inativar(Long idRestaurante) {
        buscarOuFalhar(idRestaurante).inativar();
    }

    @Transactional
    public void associarFormaPagamento(Long idRestaurante , Long idFormaPagamento) {
        Restaurante restaurante = buscarOuFalhar(idRestaurante);
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(idFormaPagamento);
        restaurante.adicionarFormaPagamento(formaPagamento);
    }

    @Transactional
    public void desassociarFormaPagamento(Long idRestaurante , Long idFormaPagamento) {
        Restaurante restaurante = buscarOuFalhar(idRestaurante);
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(idFormaPagamento);
        restaurante.removerFormaPagamento(formaPagamento);
    }

    public Restaurante buscarOuFalhar(Long restauranteId) {
        return restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(
                        String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, restauranteId)));
    }
}
