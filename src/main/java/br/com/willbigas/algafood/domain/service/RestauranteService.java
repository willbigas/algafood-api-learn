package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.domain.exception.CozinhaNaoEncontradaException;
import br.com.willbigas.algafood.domain.exception.EntidadeEmUsoException;
import br.com.willbigas.algafood.domain.exception.ProdutoNaoEncontradoException;
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
    private final UsuarioService usuarioService;

    private static final String MSG_RESTAURANTE_NAO_ENCONTRADO
            = "Não existe um cadastro de restaurante com código %d";

    public RestauranteService(RestauranteRepository restauranteRepository, CozinhaRepository cozinhaRepository, CidadeService cidadeService, FormaPagamentoService formaPagamentoService, ProdutoService produtoService, UsuarioService usuarioService) {
        this.restauranteRepository = restauranteRepository;
        this.cozinhaRepository = cozinhaRepository;
        this.cidadeService = cidadeService;
        this.formaPagamentoService = formaPagamentoService;
        this.produtoService = produtoService;
        this.usuarioService = usuarioService;
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
    public void excluir(Long idRestaurante) {
        try {
            restauranteRepository.deleteById(idRestaurante);
            restauranteRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new RestauranteNaoEncontradoException(String.format("Não existe um cadastro de restaurante com código %d ", idRestaurante));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Restaurante de código %d não pode ser removido, pois está em uso", idRestaurante));
        }
    }

    public Restaurante buscarOuFalhar(Long idRestaurante) {
        return restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(
                        String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, idRestaurante)));
    }

    public Produto buscarProduto(Restaurante restaurante, Long idProduto) {
        return restaurante.getProdutos().stream()
                .filter(p -> p.getId().equals(idProduto))
                .findFirst()
                .orElseThrow(() -> new ProdutoNaoEncontradoException(String.format("Produto %d não encontrado no Restaurante %d ", idProduto, restaurante.getId())));
    }

    @Transactional
    public void ativar(Long idRestaurante) {
        Restaurante restaurante = buscarOuFalhar(idRestaurante);
        restaurante.ativar();
        salvar(restaurante);
    }

    @Transactional
    public void inativar(Long idRestaurante) {
        Restaurante restaurante = buscarOuFalhar(idRestaurante);
        restaurante.inativar();
        salvar(restaurante);
    }

    @Transactional
    public void abrir(Long idRestaurante) {
        Restaurante restaurante = buscarOuFalhar(idRestaurante);
        restaurante.abrir();
        salvar(restaurante);
    }

    @Transactional
    public void fechar(Long idRestaurante) {
        Restaurante restaurante = buscarOuFalhar(idRestaurante);
        restaurante.fechar();
        salvar(restaurante);
    }

    @Transactional
    public void associarFormaPagamento(Long idRestaurante, Long idFormaPagamento) {
        Restaurante restaurante = buscarOuFalhar(idRestaurante);
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(idFormaPagamento);
        restaurante.adicionarFormaPagamento(formaPagamento);
        salvar(restaurante);
    }

    @Transactional
    public void desassociarFormaPagamento(Long idRestaurante, Long idFormaPagamento) {
        Restaurante restaurante = buscarOuFalhar(idRestaurante);
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(idFormaPagamento);
        restaurante.removerFormaPagamento(formaPagamento);
        salvar(restaurante);
    }

    @Transactional
    public void associarResponsavel(Long idRestaurante, Long idUsuario) {
        Restaurante restaurante = buscarOuFalhar(idRestaurante);
        Usuario usuario = usuarioService.buscarOuFalhar(idUsuario);
        restaurante.adicionarResponsavel(usuario);
        salvar(restaurante);
    }

    @Transactional
    public void desassociarResponsavel(Long idRestaurante, Long idUsuario) {
        Restaurante restaurante = buscarOuFalhar(idRestaurante);
        Usuario usuario = usuarioService.buscarOuFalhar(idUsuario);
        restaurante.removerResponsavel(usuario);
        salvar(restaurante);
    }



    @Transactional
    public void associarProduto(Long idRestaurante, Long idProduto) {
        Restaurante restaurante = buscarOuFalhar(idRestaurante);
        Produto produto = produtoService.buscarOuFalhar(idProduto);
        produto.setRestaurante(restaurante);
        restaurante.adicionarProduto(produto);
        salvar(restaurante);
    }

    @Transactional
    public void desassociarProduto(Long idRestaurante, Long idProduto) {
        Restaurante restaurante = buscarOuFalhar(idRestaurante);
        Produto produto = produtoService.buscarOuFalhar(idProduto);
        restaurante.removerProduto(produto);
        salvar(restaurante);
    }

    @Transactional
    public Restaurante adicionarProduto(Restaurante restaurante, Produto produto) {
        produto.setRestaurante(restaurante);
        produto = produtoService.salvar(produto);
        restaurante.getProdutos().add(produto);
        return restaurante;
    }


}
