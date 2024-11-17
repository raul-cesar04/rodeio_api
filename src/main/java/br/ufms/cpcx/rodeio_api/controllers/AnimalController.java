package br.ufms.cpcx.rodeio_api.controllers;

import br.ufms.cpcx.rodeio_api.dtos.AnimalDTO;
import br.ufms.cpcx.rodeio_api.models.AnimalModel;
import br.ufms.cpcx.rodeio_api.models.TropeiroModel;
import br.ufms.cpcx.rodeio_api.services.RodeioApiServiceFacade;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/animal")
public class AnimalController {
    private final RodeioApiServiceFacade rodeioApiServiceFacade;

    public AnimalController(RodeioApiServiceFacade rodeioApiServiceFacade) {
        this.rodeioApiServiceFacade = rodeioApiServiceFacade;
    }

    @GetMapping()
    public ResponseEntity<Page<AnimalModel>> listAnimals(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(rodeioApiServiceFacade.getAnimais(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalModel> getAnimal(@PathVariable Long id) {
        Optional<AnimalModel> animalModelOptional = rodeioApiServiceFacade.getAnimal(id);
        if(animalModelOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal "+id+" não encontrado.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(animalModelOptional.get());
    }

    @PostMapping()
    public ResponseEntity<AnimalModel> addAnimal(@RequestBody @Valid AnimalDTO animalDTO) {
        TropeiroModel proprietario = new TropeiroModel();
        BeanUtils.copyProperties(animalDTO.getProprietario(), proprietario);
        if(rodeioApiServiceFacade.animalExistsByNomeAndProprietario(animalDTO.getNome(), proprietario)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Animal já registrado pelo tropeiro.");
        }

        AnimalModel animalModel = new AnimalModel();
        BeanUtils.copyProperties(animalDTO, animalModel);
        animalModel.setProprietario(proprietario);

        return ResponseEntity.status(HttpStatus.CREATED).body(rodeioApiServiceFacade.createAnimal(animalModel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalModel> updateAnimal(@PathVariable Long id, @RequestBody @Valid AnimalDTO animalDTO){
        Optional<AnimalModel> animalModelOptional = rodeioApiServiceFacade.getAnimal(id);
        if(animalModelOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal "+id+" não encontrado.");
        }

        AnimalModel animalModel = new AnimalModel();

        BeanUtils.copyProperties(animalDTO, animalModel);
        animalModel.setId(animalModelOptional.get().getId());
        animalModel.setProprietario(animalModelOptional.get().getProprietario());

        return ResponseEntity.status(HttpStatus.OK).body(rodeioApiServiceFacade.createAnimal(animalModel));
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

}

