package br.ufms.cpcx.rodeio_api.controllers;


import br.ufms.cpcx.rodeio_api.dtos.TropeiroDTO;
import br.ufms.cpcx.rodeio_api.models.TropeiroModel;
import br.ufms.cpcx.rodeio_api.models.TropeiroRepresentationModelAssembler;
import br.ufms.cpcx.rodeio_api.services.RodeioApiServiceFacade;
import br.ufms.cpcx.rodeio_api.services.TropeiroService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/tropeiro")
public class TropeiroController {
    private final RodeioApiServiceFacade rodeioApiServiceFacade;
    private final TropeiroRepresentationModelAssembler tropeiroRepresentationModelAssembler;

    public TropeiroController(RodeioApiServiceFacade rodeioApiServiceFacade, TropeiroRepresentationModelAssembler tropeiroRepresentationModelAssembler) {
        this.rodeioApiServiceFacade = rodeioApiServiceFacade;
        this.tropeiroRepresentationModelAssembler = tropeiroRepresentationModelAssembler;
    }

    @GetMapping()
    public ResponseEntity<Page<EntityModel<TropeiroModel>>> listTropeiros(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.rodeioApiServiceFacade.getTropeiros(pageable).map(this::tropeiroModelToEntityModel));
    }

    @PostMapping()
    public ResponseEntity<EntityModel<TropeiroModel>> createTropeiro(@RequestBody @Valid TropeiroDTO tropeiroDTO) {
        if(this.rodeioApiServiceFacade.tropeiroExistsByNameAndSigla(tropeiroDTO.getNome(), tropeiroDTO.getSigla())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tropeiro já cadastrado com esse nome e sigla.");
        }

        TropeiroModel tropeiroModel = new TropeiroModel();
        BeanUtils.copyProperties(tropeiroDTO, tropeiroModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(tropeiroModelToEntityModel(rodeioApiServiceFacade.createTropeiro(tropeiroModel)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<TropeiroModel>> getTropeiro(@PathVariable Long id) {
        return rodeioApiServiceFacade.getTropeiroById(id).map(this::tropeiroLinkCallback).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Tropeiro id "+id+" não encontrado."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<TropeiroModel>> updateTropeiro(@PathVariable Long id, @RequestBody @Valid TropeiroDTO tropeiroDTO) {
        Optional<TropeiroModel> tropeiroModelOptional = rodeioApiServiceFacade.getTropeiroById(id);
        if(tropeiroModelOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tropeiro "+id+" não encontrado.");
        }

        TropeiroModel tropeiroModel = new TropeiroModel();
        BeanUtils.copyProperties(tropeiroDTO, tropeiroModel);
        tropeiroModel.setId(tropeiroModelOptional.get().getId());

        return ResponseEntity.status(HttpStatus.OK).body(tropeiroModelToEntityModel(rodeioApiServiceFacade.createTropeiro(tropeiroModel)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTropeiro(@PathVariable Long id) {
        Optional<TropeiroModel> tropeiroModelOptional = rodeioApiServiceFacade.getTropeiroById(id);
        if(tropeiroModelOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tropeiro "+id+" não encontrado.");
        }

        rodeioApiServiceFacade.deleteTropeiro(tropeiroModelOptional.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private ResponseEntity<EntityModel<TropeiroModel>> tropeiroLinkCallback(TropeiroModel tropeiroModel){
        return ResponseEntity.status(HttpStatus.OK).body(tropeiroModelToEntityModel(tropeiroModel));
    }

    private EntityModel<TropeiroModel> tropeiroModelToEntityModel(TropeiroModel tropeiroModel){
        Pageable pageable = PageRequest.of(0, 10);
        EntityModel<TropeiroModel> tropeiroModelEntityModel = tropeiroRepresentationModelAssembler.toModel(tropeiroModel)
                .add(linkTo(methodOn(TropeiroController.class).listTropeiros(pageable)).withRel("tropeiros"));

        return tropeiroModelEntityModel;
    }
}
