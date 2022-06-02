package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.domain.exception.CidadeNaoEncontradoException;
import br.com.willbigas.algafood.domain.exception.EntidadeEmUsoException;
import br.com.willbigas.algafood.domain.model.Cidade;
import br.com.willbigas.algafood.domain.model.Estado;
import br.com.willbigas.algafood.domain.repository.CidadeRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroCidadeService {

    private static final String MSG_CIDADE_EM_USO
            = "Cidade de código %d não pode ser removida, pois está em uso";

    private static final String MSG_CIDADE_NAO_ENCONTRADA
            = "Não existe um cadastro de cidade com código %d";

    private final CidadeRepository cidadeRepository;
    private final CadastroEstadoService cadastroEstadoService;

    public CadastroCidadeService(CidadeRepository cidadeRepository, CadastroEstadoService cadastroEstadoService) {
        this.cidadeRepository = cidadeRepository;
        this.cadastroEstadoService = cadastroEstadoService;
    }

    @Transactional
    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Estado estado = cadastroEstadoService.buscarOuFalhar(estadoId);
        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);
    }

    @Transactional
    public void excluir(Long cidadeID) {
        try {
            cidadeRepository.deleteById(cidadeID);
        } catch (EmptyResultDataAccessException e) {
            throw new CidadeNaoEncontradoException(String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeID));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, cidadeID));
        }
    }

    public Cidade buscarOuFalhar(Long cidadeId) {
        return cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new CidadeNaoEncontradoException(
                        String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId)));
    }
}
