package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.domain.exception.CozinhaNaoEncontradoException;
import br.com.willbigas.algafood.domain.exception.EntidadeEmUsoException;
import br.com.willbigas.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.willbigas.algafood.domain.model.Cozinha;
import br.com.willbigas.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CadastroCozinhaService {

    public static final String MSG_COZINHA_NAO_ENCONTRADA = "Não existe um cadastro de cozinha com código %d ";
    public static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso";

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    public void excluir(Long cozinhaId) {
        try {
            cozinhaRepository.deleteById(cozinhaId);
        } catch (EmptyResultDataAccessException e) {
            throw new CozinhaNaoEncontradoException(String.format(MSG_COZINHA_NAO_ENCONTRADA, cozinhaId));
        } catch (DataIntegrityViolationException | InvalidDataAccessResourceUsageException e) {
            throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, cozinhaId));
        }
    }

    public Cozinha buscarOuFalhar(Long id) {
        return cozinhaRepository.findById(id)
                .orElseThrow(() -> new CozinhaNaoEncontradoException(String.format(MSG_COZINHA_NAO_ENCONTRADA, id)));
    }
}
