package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.domain.exception.EntidadeEmUsoException;
import br.com.willbigas.algafood.domain.exception.EstadoNaoEncontradoException;
import br.com.willbigas.algafood.domain.model.Estado;
import br.com.willbigas.algafood.domain.repository.EstadoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroEstadoService {

    private final EstadoRepository estadoRepository;

    private static final String MSG_ESTADO_EM_USO  =
            "Estado de código %d não pode ser removido, pois está em uso";

    public CadastroEstadoService(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    @Transactional
    public Estado salvar(Estado estado) {
        return estadoRepository.save(estado);
    }

    public void excluir(Long estadoID) {
        try {
            estadoRepository.deleteById(estadoID);
        } catch (EmptyResultDataAccessException e) {
            throw new EstadoNaoEncontradoException(estadoID);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, estadoID));
        }
    }

    public Estado buscarOuFalhar(Long id) {
        return estadoRepository.findById(id)
                .orElseThrow(() -> new EstadoNaoEncontradoException(id));
    }
}
