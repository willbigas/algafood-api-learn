package br.com.willbigas.algafood.domain.repository;

import br.com.willbigas.algafood.domain.model.Estado;
import br.com.willbigas.algafood.domain.model.Permissao;

import java.util.List;

public interface EstadoRepository {

    List<Estado> listar();

    Estado buscar(Long id);

    Estado salvar(Estado permissao);

    void remover(Estado permissao);

}
