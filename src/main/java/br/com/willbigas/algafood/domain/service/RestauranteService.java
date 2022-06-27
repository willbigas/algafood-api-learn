package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.domain.exception.CozinhaNaoEncontradaException;
import br.com.willbigas.algafood.domain.exception.EntidadeEmUsoException;
import br.com.willbigas.algafood.domain.exception.RestauranteNaoEncontradoException;
import br.com.willbigas.algafood.domain.model.*;
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
    private final ProdutoService produtoService;

    private static final String MSG_RESTAURANTE_NAO_ENCONTRADO
            = "Não existe um cadastro de restaurante com código %d";

    public RestauranteService(RestauranteRepository restauranteRepository, CozinhaRepository cozinhaRepository , CidadeService cidadeService, FormaPagamentoService formaPagamentoService , ProdutoService produtoService) {
        this.restauranteRepository = restauranteRepository;
        this.cozinhaRepository = cozinhaRepository;
        this.cidadeService = cidadeService;
        this.formaPagamentoService = formaPagamentoService;
        this.produtoService = produtoService;
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

    public Restaurante buscarOuFalhar(Long restauranteId) {
        return restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(
                        String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, restauranteId)));
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

    @Transactional
    public void associarProduto(Long idRestaurante , Long idProduto) {
        Restaurante restaurante = buscarOuFalhar(idRestaurante);
        Produto produto = produtoService.buscarOuFalhar(idProduto);
        restaurante.adicionarProduto(produto);
    }

    @Transactional
    public void desassociarProduto(Long idRestaurante , Long idProduto) {
        Restaurante restaurante = buscarOuFalhar(idRestaurante);
        Produto produto = produtoService.buscarOuFalhar(idProduto);
        restaurante.removerProduto(produto);
    }


}
