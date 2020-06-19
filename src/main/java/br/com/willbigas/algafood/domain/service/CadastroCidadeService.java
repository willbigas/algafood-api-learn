package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.domain.exception.EntidadeEmUsoException;
import br.com.willbigas.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.willbigas.algafood.domain.model.Cidade;
import br.com.willbigas.algafood.domain.model.Cozinha;
import br.com.willbigas.algafood.domain.model.Estado;
import br.com.willbigas.algafood.domain.model.Restaurante;
import br.com.willbigas.algafood.domain.repository.CidadeRepository;
import br.com.willbigas.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private EstadoRepository estadoRepository;

    public Cidade salvar(Cidade cidade) {
        Long estadoID = cidade.getEstado().getId();
        Estado estado = estadoRepository.findById(estadoID).get();
        if (estado == null) {
            throw new EntidadeNaoEncontradaException(String.format("Não existe cadastro de estado com o código %d" , estadoID));
        }

        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);
    }

    public void excluir(Long cidadeID) {
        try {
            cidadeRepository.deleteById(cidadeID);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de cidade com código %d ", cidadeID));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Cidade de código %d não pode ser removida, pois está em uso", cidadeID));
        }
    }
}
