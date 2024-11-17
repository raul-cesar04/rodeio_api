package br.ufms.cpcx.rodeio_api.models;

import br.ufms.cpcx.rodeio_api.controllers.EventoController;
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
    @Override
    public void addLinks(EntityModel<EventoModel> resource) {
        Long id = resource.getContent().getId();

        resource.add(linkTo(methodOn(EventoController.class).findEvento(id)).withSelfRel());

    }

    @Override
    public void addLinks(CollectionModel<EntityModel<EventoModel>> resources) {
        Pageable pageable = PageRequest.of(0, 10);
        resources.add(linkTo(methodOn(EventoController.class).listEventos(pageable)).withRel("eventos"));
    }
}
