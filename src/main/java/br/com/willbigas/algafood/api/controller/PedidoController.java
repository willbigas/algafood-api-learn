package br.com.willbigas.algafood.api.controller;

import br.com.willbigas.algafood.api.mapper.PedidoMapper;
import br.com.willbigas.algafood.api.model.request.PedidoRequestDTO;
import br.com.willbigas.algafood.api.model.response.PedidoResponseDTO;
import br.com.willbigas.algafood.api.model.response.PedidoResumidoResponseDTO;
import br.com.willbigas.algafood.core.data.PageableTranslator;
import br.com.willbigas.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.willbigas.algafood.domain.exception.NegocioException;
import br.com.willbigas.algafood.domain.model.Pedido;
import br.com.willbigas.algafood.domain.model.Usuario;
import br.com.willbigas.algafood.domain.repository.filter.PedidoFilter;
import br.com.willbigas.algafood.domain.repository.spec.PedidoSpecification;
import br.com.willbigas.algafood.domain.service.PedidoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoMapper pedidoMapper;

    public PedidoController(PedidoService pedidoService, PedidoMapper pedidoMapper) {
        this.pedidoService = pedidoService;
        this.pedidoMapper = pedidoMapper;
    }

    @GetMapping
    public List<PedidoResumidoResponseDTO> pesquisar(PedidoFilter pedidoFilter) {

        Specification<Pedido> pedidoSpecification = PedidoSpecification.usandoFiltro(pedidoFilter);
        List<Pedido> pedidos = pedidoService.findAll(pedidoSpecification);
        return pedidoMapper.toPedidoResumidoList(pedidos);
    }

    @GetMapping("/page")
    public Page<PedidoResumidoResponseDTO> pesquisar(PedidoFilter pedidoFilter , Pageable pageable) {
        pageable = traduzirPageable(pageable);
        Specification<Pedido> pedidoSpecification = PedidoSpecification.usandoFiltro(pedidoFilter);
        return pedidoMapper.toPageDTO(pedidoService.findAll(pedidoSpecification , pageable));
    }

//    @GetMapping
//    public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
//        List<Pedido> pedidos = pedidoService.findAll();
//        List<PedidoResumidoResponseDTO> pedidoResumidoResponseDTOS = pedidoMapper.toPedidoResumidoList(pedidos);
//
//        MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidoResumidoResponseDTOS);
//
//        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//        filterProvider.addFilter("pedidoFilter" , SimpleBeanPropertyFilter.serializeAll());
//
//        if (StringUtils.isNotBlank(campos)) {
//            filterProvider.addFilter("pedidoFilter" , SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//        }
//
//        pedidosWrapper.setFilters(filterProvider);
//
//        return pedidosWrapper;
//    }

    @GetMapping("/{codigoPedido}")
    public PedidoResponseDTO buscar(@PathVariable String codigoPedido) {
        return pedidoMapper.toResponseDTO(pedidoService.buscarOuFalhar(codigoPedido));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponseDTO adicionar(@Valid @RequestBody PedidoRequestDTO pedidoRequestDTO) {
        try {
            Pedido novoPedido = pedidoMapper.toPedido(pedidoRequestDTO);

            // TODO pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            novoPedido = pedidoService.emitir(novoPedido);
            return pedidoMapper.toResponseDTO(novoPedido);

        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    /**
     * Metodo para traduzir os campos de DTO para Entity para que a ordenação default do spring funcione corretamente.
     * @param pageable
     * @return
     */
    private Pageable traduzirPageable(Pageable pageable) {
        var propriedades = Map.of(
                "codigo", "codigo",
                "restaurante.nome", "restaurante.nome",
                "nomeCliente", "cliente.nome",
                "valorTotal", "valorTotal"
        );
        return PageableTranslator.translate(pageable, propriedades);
    }

}
