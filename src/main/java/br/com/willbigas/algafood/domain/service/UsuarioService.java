package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.domain.exception.EntidadeEmUsoException;
import br.com.willbigas.algafood.domain.exception.UsuarioNaoEncontradoException;
import br.com.willbigas.algafood.domain.model.Grupo;
import br.com.willbigas.algafood.domain.model.Usuario;
import br.com.willbigas.algafood.domain.repository.UsuarioRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private static final String MSG_USUARIO_EM_USO
            = "Usuário de código %d não pode ser removido, pois está em uso";

    private static final String MSG_USUARIO_NAO_ENCONTRADO
            = "Não existe um cadastro de usuário com código %d";

    private final UsuarioRepository usuarioRepository;
    private final GrupoService grupoService;

    public UsuarioService(UsuarioRepository usuarioRepository, GrupoService grupoService) {
        this.usuarioRepository = usuarioRepository;
        this.grupoService = grupoService;
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public void excluir(Long idUsuario) {
        try {
            usuarioRepository.deleteById(idUsuario);
            usuarioRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new UsuarioNaoEncontradoException(String.format(MSG_USUARIO_NAO_ENCONTRADO, idUsuario));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_USUARIO_EM_USO, idUsuario));
        }
    }

    public Usuario buscarOuFalhar(Long idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(
                        String.format(MSG_USUARIO_NAO_ENCONTRADO, idUsuario)));
    }

    @Transactional
    public void associarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = buscarOuFalhar(usuarioId);
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);
        usuario.adicionarGrupo(grupo);
        salvar(usuario);
    }

    @Transactional
    public void desassociarGrupo(Long idUsuario, Long idGrupo) {
        Usuario usuario = buscarOuFalhar(idUsuario);
        Grupo grupo = grupoService.buscarOuFalhar(idGrupo);
        usuario.removerGrupo(grupo);
        salvar(usuario);
    }




}
