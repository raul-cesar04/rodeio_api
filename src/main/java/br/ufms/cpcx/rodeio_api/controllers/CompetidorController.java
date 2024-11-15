package br.ufms.cpcx.rodeio_api.controllers;

import br.ufms.cpcx.rodeio_api.dtos.CompetidorDTO;
import br.ufms.cpcx.rodeio_api.models.CompetidorModel;
import br.ufms.cpcx.rodeio_api.models.CompetidorRepresentationModelAssembler;
import br.ufms.cpcx.rodeio_api.services.RodeioApiServiceFacade;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
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
@RequestMapping("/v1/competidor")
public class CompetidorController {
    private final RodeioApiServiceFacade rodeioApiServiceFacade;
    private final CompetidorRepresentationModelAssembler competidorRepresentationModelAssembler;
    private final PagedResourcesAssembler pagedResourcesAssembler;

    public CompetidorController(RodeioApiServiceFacade rodeioApiServiceFacade, CompetidorRepresentationModelAssembler competidorRepresentationModelAssembler, PagedResourcesAssembler pagedResourcesAssembler) {
        this.rodeioApiServiceFacade = rodeioApiServiceFacade;
        this.competidorRepresentationModelAssembler = competidorRepresentationModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @PostMapping()
    public ResponseEntity<EntityModel<CompetidorModel>> createCompetidor(@RequestBody @Valid CompetidorDTO competidorDTO){
        CompetidorModel competidorModel = new CompetidorModel();
        BeanUtils.copyProperties(competidorDTO, competidorModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(competidorModelToEntityModel(rodeioApiServiceFacade.createCompetidor(competidorModel)));
    }

    @GetMapping()
    public ResponseEntity<Object> listCompetidores(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
       return ResponseEntity.status(HttpStatus.OK).body(rodeioApiServiceFacade.getCompetidores(pageable).map(this::competidorModelToEntityModel));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CompetidorModel>> getCompetidor(@PathVariable Long id) {
        return rodeioApiServiceFacade.getCompetidorById(id).map(this::competidorLinkCallback).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Competidor "+id+" não encontrado."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<CompetidorModel>> updateCompetidor(@PathVariable Long id, @RequestBody @Valid CompetidorDTO competidorDTO) {
        Optional<CompetidorModel> competidorModelOptional = rodeioApiServiceFacade.getCompetidorById(id);

        if(competidorModelOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Competidor "+id+" não encontrado.");
        }

        CompetidorModel competidorModel = new CompetidorModel();
        BeanUtils.copyProperties(competidorDTO, competidorModel);
        competidorModel.setId(competidorModelOptional.get().getId());

        return ResponseEntity.status(HttpStatus.OK).body(competidorModelToEntityModel(rodeioApiServiceFacade.createCompetidor(competidorModel)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCompetidor(@PathVariable Long id) {
        Optional<CompetidorModel> competidorModelOptional = rodeioApiServiceFacade.getCompetidorById(id);

        if(competidorModelOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Competidor "+id+" não encontrado.");
        }

        rodeioApiServiceFacade.deleteCompetidor(competidorModelOptional.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Competidor "+id+" removido.");
    }

    private ResponseEntity<EntityModel<CompetidorModel>> competidorLinkCallback(CompetidorModel competidorModel){
        return ResponseEntity.status(HttpStatus.OK).body(competidorModelToEntityModel(competidorModel));
    }

    private EntityModel<CompetidorModel> competidorModelToEntityModel(CompetidorModel competidorModel){
        Pageable pageable = PageRequest.of(0, 10);
        EntityModel<CompetidorModel> competidorModelEntityModel = competidorRepresentationModelAssembler.toModel(competidorModel)
                .add(linkTo(methodOn(CompetidorController.class).listCompetidores(pageable)).withRel("competidores"));
        return competidorModelEntityModel;
    }
}
