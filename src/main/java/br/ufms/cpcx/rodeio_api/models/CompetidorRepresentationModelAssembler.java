package br.ufms.cpcx.rodeio_api.models;

import br.ufms.cpcx.rodeio_api.controllers.CompetidorController;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CompetidorRepresentationModelAssembler implements SimpleRepresentationModelAssembler<CompetidorModel> {
    @Override
    public void addLinks(EntityModel<CompetidorModel> resource){
        Long id = resource.getContent().getId();

        resource.add(linkTo(methodOn(CompetidorController.class).getCompetidor(id)).withSelfRel());
        // TODO: Adicionar o negocio aqui de inscricoes quando for feita

    }

    @Override
    public void addLinks(CollectionModel<EntityModel<CompetidorModel>> resources){
        var pageable = PageRequest.of(0, 10);
        resources.add(linkTo(methodOn(CompetidorController.class).listCompetidores(pageable)).withSelfRel());
    }
}
