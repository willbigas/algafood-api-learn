package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.domain.exception.PermissaoNaoEncontradaException;
import br.com.willbigas.algafood.domain.model.Permissao;
import br.com.willbigas.algafood.domain.repository.PermissaoRepository;
import org.springframework.stereotype.Service;

@Service
public class PermissaoService {

    private final PermissaoRepository permissaoRepository;

    public PermissaoService(PermissaoRepository permissaoRepository) {
        this.permissaoRepository = permissaoRepository;
    }

    public Permissao buscarOuFalhar(Long idPermissao) {
        return permissaoRepository.findById(idPermissao)
                .orElseThrow(() -> new PermissaoNaoEncontradaException(idPermissao));
    }
}
