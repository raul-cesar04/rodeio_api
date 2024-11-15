package br.ufms.cpcx.rodeio_api.models;

import br.ufms.cpcx.rodeio_api.controllers.CompetidorController;
import br.ufms.cpcx.rodeio_api.controllers.TropeiroController;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TropeiroRepresentationModelAssembler implements SimpleRepresentationModelAssembler<TropeiroModel> {
    @Override
    public void addLinks(EntityModel<TropeiroModel> resource){
        Long id = resource.getContent().getId();

        resource.add(linkTo(methodOn(TropeiroController.class).getTropeiro(id)).withSelfRel());

        // TODO: Add Relacionamentos

    }

    @Override
    public void addLinks(CollectionModel<EntityModel<TropeiroModel>> resources){
        Pageable pageable = PageRequest.of(0, 10);
        resources.add(linkTo(methodOn(TropeiroController.class).listTropeiros(pageable)).withSelfRel());
    }
}
