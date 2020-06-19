package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.domain.exception.EntidadeEmUsoException;
import br.com.willbigas.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.willbigas.algafood.domain.model.Cozinha;
import br.com.willbigas.algafood.domain.model.Restaurante;
import br.com.willbigas.algafood.domain.repository.CozinhaRepository;
import br.com.willbigas.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não existe cadastro de cozinha com o código %d", cozinhaId)));

        restaurante.setCozinha(cozinha);
        return restauranteRepository.save(restaurante);
    }

    public void excluir(Long restauranteID) {
        try {
            restauranteRepository.deleteById(restauranteID);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de cozinha com código %d ", restauranteID));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Cozinha de código %d não pode ser removida, pois está em uso", restauranteID));
        }
    }
}
