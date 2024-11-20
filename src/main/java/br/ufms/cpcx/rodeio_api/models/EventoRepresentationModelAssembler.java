package br.ufms.cpcx.rodeio_api.models;

import br.ufms.cpcx.rodeio_api.controllers.AnimalController;
import br.ufms.cpcx.rodeio_api.controllers.CompetidorController;
import br.ufms.cpcx.rodeio_api.controllers.EventoController;
import br.ufms.cpcx.rodeio_api.controllers.TropeiroController;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EventoRepresentationModelAssembler implements SimpleRepresentationModelAssembler<EventoModel> {
    private static final Pageable pageable = PageRequest.of(0, 10);
    @Override
    public void addLinks(EntityModel<EventoModel> resource) {
        Long id = resource.getContent().getId();

        resource.add(linkTo(methodOn(EventoController.class).findEvento(id)).withSelfRel());

        resource.add(linkTo(methodOn(EventoController.class).listEventos(pageable)).withRel("eventos"));
        resource.add(linkTo(methodOn(CompetidorController.class).listCompetidores(pageable)).withRel("competidores"));
        resource.add(linkTo(methodOn(TropeiroController.class).listTropeiros(pageable)).withRel("tropeiros"));
        resource.add(linkTo(methodOn(AnimalController.class).listAnimals(pageable)).withRel("animais"));
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<EventoModel>> resources) {
        resources.forEach(this::addLinks);
    }
}
