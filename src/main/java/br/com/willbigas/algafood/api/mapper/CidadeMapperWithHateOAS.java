package br.com.willbigas.algafood.api.mapper;

import br.com.willbigas.algafood.api.controller.CidadeController;
import br.com.willbigas.algafood.api.controller.EstadoController;
import br.com.willbigas.algafood.api.model.response.CidadeResponseDTO;
import br.com.willbigas.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CidadeMapperWithHateOAS extends RepresentationModelAssemblerSupport<Cidade, CidadeResponseDTO> {

    private final ModelMapper modelMapper;

    public CidadeMapperWithHateOAS(ModelMapper modelMapper) {
        super(CidadeController.class, CidadeResponseDTO.class);
        this.modelMapper = modelMapper;
    }

    @Override
    public CidadeResponseDTO toModel(Cidade cidade) {
        CidadeResponseDTO responseDTO = createModelWithId(cidade.getId() , cidade);
        modelMapper.map(cidade , responseDTO);

        responseDTO.add(linkTo(methodOn(CidadeController.class)
                .listar())
                .withRel("cidades"));

        responseDTO.getEstado().add(linkTo(methodOn(EstadoController.class)
                .buscar(responseDTO.getEstado().getId()))
                .withSelfRel());

        return responseDTO;
    }

    @Override
    public CollectionModel<CidadeResponseDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(CidadeController.class).withSelfRel());
    }
}
