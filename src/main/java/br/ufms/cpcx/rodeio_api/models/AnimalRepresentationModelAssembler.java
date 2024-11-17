package br.ufms.cpcx.rodeio_api.models;

import br.ufms.cpcx.rodeio_api.controllers.AnimalController;
import br.ufms.cpcx.rodeio_api.controllers.TropeiroController;
import br.ufms.cpcx.rodeio_api.dtos.TropeiroDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AnimalRepresentationModelAssembler implements SimpleRepresentationModelAssembler<AnimalModel> {
    @Override
    public void addLinks(EntityModel<AnimalModel> resource) {
        Long id = resource.getContent().getId();
        Long tropeiroId = resource.getContent().getProprietario().getId();

        resource.add(linkTo(methodOn(AnimalController.class).getAnimal(id)).withSelfRel());
        resource.add(linkTo(methodOn(TropeiroController.class).getTropeiro(tropeiroId)).withRel("proprietario"));
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<AnimalModel>> resources) {
        Pageable pageable = PageRequest.of(0, 10);

        resources.add(linkTo(methodOn(AnimalController.class).listAnimals(pageable)).withRel("animais"));
        resources.add(linkTo(methodOn(TropeiroController.class).listTropeiros(pageable)).withRel("tropeiros"));
    }
}
