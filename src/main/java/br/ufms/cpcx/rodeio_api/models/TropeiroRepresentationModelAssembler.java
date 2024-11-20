package br.ufms.cpcx.rodeio_api.models;

import br.ufms.cpcx.rodeio_api.controllers.AnimalController;
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
        Pageable pageable = PageRequest.of(0, 10);
        Long id = resource.getContent().getId();

        resource.add(linkTo(methodOn(TropeiroController.class).getTropeiro(id)).withSelfRel());
        resource.add(linkTo(methodOn(TropeiroController.class).listTropeiros(pageable)).withRel("tropeiros"));
        resource.add(linkTo(methodOn(AnimalController.class).listAnimals(pageable)).withRel("animais"));

    }

    @Override
    public void addLinks(CollectionModel<EntityModel<TropeiroModel>> resources){
        resources.forEach(this::addLinks);
    }
}
