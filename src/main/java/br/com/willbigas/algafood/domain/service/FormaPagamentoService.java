package br.com.willbigas.algafood.domain.service;

import br.com.willbigas.algafood.domain.exception.EntidadeEmUsoException;
import br.com.willbigas.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import br.com.willbigas.algafood.domain.model.FormaPagamento;
import br.com.willbigas.algafood.domain.repository.FormaPagamentoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FormaPagamentoService {

    private static final String MSG_FORMA_PAGAMENTO_EM_USO
            = "Forma de pagamento de código %d não pode ser removida, pois está em uso";

    private static final String MSG_FORMA_PAGAMENTO_NAO_ENCONTRADA
            = "Não existe um cadastro de forma de pagamento com o código %d";

    private final FormaPagamentoRepository formaPagamentoRepository;

    public FormaPagamentoService(FormaPagamentoRepository formaPagamentoRepository) {
        this.formaPagamentoRepository = formaPagamentoRepository;
    }

    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return formaPagamentoRepository.save(formaPagamento);
    }

    @Transactional
    public void excluir(Long idFormaPagamento) {
        try {
            formaPagamentoRepository.deleteById(idFormaPagamento);
            formaPagamentoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new FormaPagamentoNaoEncontradaException(String.format(MSG_FORMA_PAGAMENTO_NAO_ENCONTRADA, idFormaPagamento));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_FORMA_PAGAMENTO_EM_USO, idFormaPagamento));
        }
    }

    public FormaPagamento buscarOuFalhar(Long idFormaPagamento) {
        return formaPagamentoRepository.findById(idFormaPagamento)
                .orElseThrow(() -> new FormaPagamentoNaoEncontradaException(
                        String.format(MSG_FORMA_PAGAMENTO_NAO_ENCONTRADA, idFormaPagamento)));
    }
}
