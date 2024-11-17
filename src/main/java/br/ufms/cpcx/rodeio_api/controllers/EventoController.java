package br.ufms.cpcx.rodeio_api.controllers;

import br.ufms.cpcx.rodeio_api.dtos.EventoDTO;
import br.ufms.cpcx.rodeio_api.models.EventoModel;
import br.ufms.cpcx.rodeio_api.models.EventoRepresentationModelAssembler;
import br.ufms.cpcx.rodeio_api.services.EventoService;
import br.ufms.cpcx.rodeio_api.services.RodeioApiServiceFacade;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/evento")
public class EventoController {
    private final RodeioApiServiceFacade rodeioApiServiceFacade;
    private final EventoRepresentationModelAssembler eventoRepresentationModelAssembler;
    private final EventoService eventoService;

    public EventoController(RodeioApiServiceFacade rodeioApiServiceFacade, EventoRepresentationModelAssembler eventoRepresentationModelAssembler, EventoService eventoService) {
        this.rodeioApiServiceFacade = rodeioApiServiceFacade;
        this.eventoRepresentationModelAssembler = eventoRepresentationModelAssembler;
        this.eventoService = eventoService;
    }

    @GetMapping()
    public ResponseEntity<Page<EntityModel<EventoModel>>> listEventos(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(rodeioApiServiceFacade.getEventos(pageable).map(this::eventoModelToEntityModel));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<EventoModel>> findEvento(@PathVariable Long id) {
        return rodeioApiServiceFacade.getEvento(id).map(this::eventoLinkCallback).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento "+id+" não foi encontrado."));
    }

    @PostMapping()
    public ResponseEntity<EntityModel<EventoModel>> createEvento(@RequestBody @Valid EventoDTO eventoDTO) {
        if(rodeioApiServiceFacade.eventoExistsByTituloAndLocalizacao(eventoDTO.getTitulo(), eventoDTO.getLocalizacao())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Evento já cadastrado nessa data e titulo.");
        }

        EventoModel eventoModel = new EventoModel();
        BeanUtils.copyProperties(eventoDTO, eventoModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(this.eventoModelToEntityModel(rodeioApiServiceFacade.createEvento(eventoModel)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<EventoModel>> updateEvento(@PathVariable Long id, @RequestBody @Valid EventoDTO eventoDTO) {
        return rodeioApiServiceFacade.getEvento(id)
                .map(eventoModel->{
                    EventoModel eventoModelUpdate = new EventoModel();

                    BeanUtils.copyProperties(eventoDTO, eventoModelUpdate);
                    eventoModelUpdate.setId(eventoModel.getId());

                    return eventoLinkCallback(rodeioApiServiceFacade.createEvento(eventoModelUpdate));
                }).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento "+id+" não foi encontrado."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEvento(@PathVariable Long id) {
        return rodeioApiServiceFacade.getEvento(id)
                .map(eventoModel->{
                    rodeioApiServiceFacade.deleteEvento(eventoModel);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento "+id+" não foi encontrado."));
    }

    private ResponseEntity<EntityModel<EventoModel>> eventoLinkCallback(EventoModel eventoModel) {
        return ResponseEntity.status(HttpStatus.OK).body(eventoModelToEntityModel(eventoModel));
    }
    private EntityModel<EventoModel> eventoModelToEntityModel(EventoModel eventoModel) {
        Pageable pageable = PageRequest.of(0, 10);
        EntityModel<EventoModel> eventoModelEntityModel = eventoRepresentationModelAssembler.toModel(eventoModel)
                .add(linkTo(methodOn(EventoController.class).listEventos(pageable)).withRel("eventos"));

        return eventoModelEntityModel;
    }
}
