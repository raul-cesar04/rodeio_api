package br.ufms.cpcx.rodeio_api.models;

import br.ufms.cpcx.rodeio_api.controllers.CompetidorController;
import br.ufms.cpcx.rodeio_api.controllers.EventoCompetidorInscricaoController;
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
        var pageable = PageRequest.of(0, 10);
        Long id = resource.getContent().getId();

        resource.add(linkTo(methodOn(CompetidorController.class).getCompetidor(id)).withSelfRel());
        resource.add(linkTo(methodOn(CompetidorController.class).listCompetidores(pageable)).withRel("competidores"));
        resource.add(linkTo(methodOn(EventoCompetidorInscricaoController.class).listarEventos(id)).withRel("eventos_inscritos"));

    }

    @Override
    public void addLinks(CollectionModel<EntityModel<CompetidorModel>> resources){
        resources.forEach(this::addLinks);
    }
}
