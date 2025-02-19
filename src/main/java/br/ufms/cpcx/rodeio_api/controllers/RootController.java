package br.ufms.cpcx.rodeio_api.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/")
public class RootController {
    @GetMapping("/")
    public RepresentationModel root(){
        var rootResource = new RepresentationModel();
        var pageable = PageRequest.of(0, 10);

        rootResource.add(linkTo(methodOn(RootController.class).root()).withSelfRel());

        rootResource.add(linkTo(methodOn(CompetidorController.class).listCompetidores(pageable)).withRel("competidores"));

        rootResource.add(linkTo(methodOn(TropeiroController.class).listTropeiros(pageable)).withRel("tropeiros"));

        rootResource.add(linkTo(methodOn(AnimalController.class).listAnimals(pageable)).withRel("animais"));

        rootResource.add(linkTo(methodOn(EventoController.class).listEventos(pageable)).withRel("eventos"));
        return rootResource;
    }
}
