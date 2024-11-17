package br.ufms.cpcx.rodeio_api.controllers;

import br.ufms.cpcx.rodeio_api.dtos.AnimalDTO;
import br.ufms.cpcx.rodeio_api.models.AnimalModel;
import br.ufms.cpcx.rodeio_api.models.AnimalRepresentationModelAssembler;
import br.ufms.cpcx.rodeio_api.models.TropeiroModel;
import br.ufms.cpcx.rodeio_api.services.RodeioApiServiceFacade;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/animal")
public class AnimalController {
    private final RodeioApiServiceFacade rodeioApiServiceFacade;
    private final AnimalRepresentationModelAssembler animalRepresentationModelAssembler;

    public AnimalController(RodeioApiServiceFacade rodeioApiServiceFacade, AnimalRepresentationModelAssembler animalRepresentationModelAssembler) {
        this.rodeioApiServiceFacade = rodeioApiServiceFacade;
        this.animalRepresentationModelAssembler = animalRepresentationModelAssembler;
    }

    @GetMapping()
    public ResponseEntity<Page<EntityModel<AnimalModel>>> listAnimals(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(rodeioApiServiceFacade.getAnimais(pageable).map(this::animalModelToEntityModel));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<AnimalModel>> getAnimal(@PathVariable Long id) {
        return rodeioApiServiceFacade.getAnimal(id).map(this::competidorLinkCallback).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal "+id+" não encontrado."));
    }

    @PostMapping()
    public ResponseEntity<EntityModel<AnimalModel>> addAnimal(@RequestBody @Valid AnimalDTO animalDTO) {
        TropeiroModel proprietario = new TropeiroModel();
        BeanUtils.copyProperties(animalDTO.getProprietario(), proprietario);
        if(rodeioApiServiceFacade.animalExistsByNomeAndProprietario(animalDTO.getNome(), proprietario)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Animal já registrado pelo tropeiro.");
        }

        AnimalModel animalModel = new AnimalModel();
        BeanUtils.copyProperties(animalDTO, animalModel);
        animalModel.setProprietario(proprietario);

        return ResponseEntity.status(HttpStatus.CREATED).body(animalModelToEntityModel(rodeioApiServiceFacade.createAnimal(animalModel)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<AnimalModel>> updateAnimal(@PathVariable Long id, @RequestBody @Valid AnimalDTO animalDTO){
        Optional<AnimalModel> animalModelOptional = rodeioApiServiceFacade.getAnimal(id);
        if(animalModelOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal "+id+" não encontrado.");
        }

        AnimalModel animalModel = new AnimalModel();

        BeanUtils.copyProperties(animalDTO, animalModel);
        animalModel.setId(animalModelOptional.get().getId());
        animalModel.setProprietario(animalModelOptional.get().getProprietario());

        return ResponseEntity.status(HttpStatus.OK).body(animalModelToEntityModel(rodeioApiServiceFacade.createAnimal(animalModel)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAnimal(@PathVariable Long id){
        Optional<AnimalModel> animalOptional = rodeioApiServiceFacade.getAnimal(id);
        if(animalOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal "+id+" não encontrado.");
        }

        rodeioApiServiceFacade.deleteAnimal(animalOptional.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    private ResponseEntity<EntityModel<AnimalModel>> competidorLinkCallback(AnimalModel animalModel){
        return ResponseEntity.status(HttpStatus.OK).body(animalModelToEntityModel(animalModel));
    }

    private EntityModel<AnimalModel> animalModelToEntityModel(AnimalModel animalModel){
        Pageable pageable = PageRequest.of(0, 10);
        EntityModel<AnimalModel> animalModelEntityModel = animalRepresentationModelAssembler.toModel(animalModel)
                .add(linkTo(methodOn(AnimalController.class).listAnimals(pageable)).withRel("animais"));
        return animalModelEntityModel;
    }
}

