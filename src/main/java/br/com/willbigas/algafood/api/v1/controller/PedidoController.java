package br.com.willbigas.algafood.api.v1.controller;

import br.com.willbigas.algafood.api.v1.mapper.PedidoMapper;
import br.com.willbigas.algafood.api.v1.model.request.PedidoRequestDTO;
import br.com.willbigas.algafood.api.v1.model.response.PedidoResponseDTO;
import br.com.willbigas.algafood.api.v1.model.response.PedidoResumidoResponseDTO;
import br.com.willbigas.algafood.core.data.PageableTranslator;
import br.com.willbigas.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.willbigas.algafood.domain.exception.NegocioException;
import br.com.willbigas.algafood.domain.model.Pedido;
import br.com.willbigas.algafood.domain.model.Usuario;
import br.com.willbigas.algafood.domain.filter.PedidoFilter;
import br.com.willbigas.algafood.domain.repository.spec.PedidoSpecification;
import br.com.willbigas.algafood.domain.service.PedidoService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
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

    @Parameter(name = "campos" , description = "Campos de filtro personalizado de todas as colunas" , example = "nomeDaColuna,nomeDaColuna2,objeto[nomeDaColuna]")
    @GetMapping("/page")
    public Page<PedidoResumidoResponseDTO> pesquisar(PedidoFilter pedidoFilter, Pageable pageable) {
        pageable = traduzirPageable(pageable);
        Specification<Pedido> pedidoSpecification = PedidoSpecification.usandoFiltro(pedidoFilter);
        return pedidoMapper.toPageDTO(pedidoService.findAll(pedidoSpecification, pageable));
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
     *
     * @param pageable
     * @return
     */
    private Pageable traduzirPageable(Pageable pageable) {
        Map<String, String> propriedades = new HashMap<>();
        propriedades.put("codigo", "codigo");
        propriedades.put("restaurante.nome", "restaurante.nome");
        propriedades.put("nomeCliente", "cliente.nome");
        propriedades.put("valorTotal", "valorTotal");
        return PageableTranslator.translate(pageable, propriedades);
    }

}
