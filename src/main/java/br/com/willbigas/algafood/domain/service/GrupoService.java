package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.domain.exception.EntidadeEmUsoException;
import br.com.willbigas.algafood.domain.exception.GrupoNaoEncontradoException;
import br.com.willbigas.algafood.domain.model.Grupo;
import br.com.willbigas.algafood.domain.model.Permissao;
import br.com.willbigas.algafood.domain.repository.GrupoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GrupoService {

    private static final String MSG_GRUPO_EM_USO
            = "Grupo de permissão de código %d não pode ser removido, pois está em uso";

    private static final String MSG_GRUPO_NAO_ENCONTRADO
            = "Não existe um cadastro de grupo de permissão com o código %d";

    private final GrupoRepository grupoRepository;
    private final PermissaoService permissaoService;


    public GrupoService(GrupoRepository grupoRepository, PermissaoService permissaoService) {
        this.grupoRepository = grupoRepository;
        this.permissaoService = permissaoService;
    }

    @Transactional
    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    public List<Grupo> findAll() {
        return grupoRepository.findAll();
    }

    public Grupo buscarOuFalhar(Long idGrupo) {
        return grupoRepository.findById(idGrupo)
                .orElseThrow(() -> new GrupoNaoEncontradoException(idGrupo));
    }

    @Transactional
    public void excluir(Long idGrupo) {
        try {
            grupoRepository.deleteById(idGrupo);
            grupoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new GrupoNaoEncontradoException(String.format(MSG_GRUPO_NAO_ENCONTRADO, idGrupo));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, idGrupo));
        }
    }

    @Transactional
    public void desassociarPermissao(Long idGrupo, Long idPermissao) {
        Grupo grupo = buscarOuFalhar(idGrupo);
        Permissao permissao = permissaoService.buscarOuFalhar(idPermissao);
        grupo.removerPermissao(permissao);
        salvar(grupo);
    }

    @Transactional
    public void associarPermissao(Long idGrupo, Long idPermissao) {
        Grupo grupo = buscarOuFalhar(idGrupo);
        Permissao permissao = permissaoService.buscarOuFalhar(idPermissao);
        grupo.adicionarPermissao(permissao);
        salvar(grupo);
    }

}
