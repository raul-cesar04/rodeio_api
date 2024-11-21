package br.ufms.cpcx.rodeio_api.controllers;

import br.ufms.cpcx.rodeio_api.dtos.CompetidorDTO;
import br.ufms.cpcx.rodeio_api.dtos.EventoDTO;
import br.ufms.cpcx.rodeio_api.models.CompetidorModel;
import br.ufms.cpcx.rodeio_api.models.EventoCompetidorInscricaoModel;
import br.ufms.cpcx.rodeio_api.models.EventoModel;
import br.ufms.cpcx.rodeio_api.services.EventoCompetidorInscricaoService;
import br.ufms.cpcx.rodeio_api.services.RodeioApiServiceFacade;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/competidor")
public class EventoCompetidorInscricaoController {
    private final RodeioApiServiceFacade rodeioApiServiceFacade;
    private final EventoCompetidorInscricaoService eventoCompetidorInscricaoService;

    public EventoCompetidorInscricaoController(RodeioApiServiceFacade rodeioApiServiceFacade, EventoCompetidorInscricaoService eventoCompetidorInscricaoService) {
        this.rodeioApiServiceFacade = rodeioApiServiceFacade;
        this.eventoCompetidorInscricaoService = eventoCompetidorInscricaoService;
    }

    @GetMapping("/{id}/evento")
    public ResponseEntity<Object> listarEventos(@PathVariable Long id) {
        Optional<CompetidorModel> competidorModelOptional = rodeioApiServiceFacade.getCompetidorById(id);

        if(competidorModelOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Competidor "+id+" não foi encontrado.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(competidorModelOptional.get().getEventos());
    }

    @PostMapping("/{id}/evento")
    public ResponseEntity<Object> criarInscricao(@PathVariable Long id, @RequestBody EventoDTO eventoDTO) {
        Optional<CompetidorModel> competidorModelOptional = rodeioApiServiceFacade.getCompetidorById(id);

        if(competidorModelOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Competidor "+id+" não foi encontrado.");
        }

        Optional<EventoModel> eventoModelOptional = rodeioApiServiceFacade.getEvento(eventoDTO.getId());

        if(eventoModelOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento "+eventoDTO.getId()+" não econtrado.");
        }

        if(rodeioApiServiceFacade.findEventoCompetidorInscricaoByCompetidorAndEvento(competidorModelOptional.get(), eventoModelOptional.get()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Competidor já inscrito nesse evento.");
        }

        EventoCompetidorInscricaoModel eventoCompetidorInscricaoModel = new EventoCompetidorInscricaoModel();
        eventoCompetidorInscricaoModel.setCompetidor(competidorModelOptional.get());
        eventoCompetidorInscricaoModel.setEvento(eventoModelOptional.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(rodeioApiServiceFacade.createEventoCompetidorInscricao(eventoCompetidorInscricaoModel));
    }
}
