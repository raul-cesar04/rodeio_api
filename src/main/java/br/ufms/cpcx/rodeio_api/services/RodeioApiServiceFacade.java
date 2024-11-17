package br.ufms.cpcx.rodeio_api.services;

import br.ufms.cpcx.rodeio_api.models.AnimalModel;
import br.ufms.cpcx.rodeio_api.models.CompetidorModel;
import br.ufms.cpcx.rodeio_api.models.TropeiroModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RodeioApiServiceFacade {
    private final CompetidorService competidorService;
    private final TropeiroService tropeiroService;

    private final AnimalService animalService;

    public RodeioApiServiceFacade(CompetidorService ccompetidorService, TropeiroService tropeiroService, AnimalService animalService) {
        this.competidorService = ccompetidorService;
        this.tropeiroService = tropeiroService;
        this.animalService = animalService;
    }

    /** COMPETIDORES **/
    public Page<CompetidorModel> getCompetidores(Pageable pageable){
        return this.competidorService.findAll(pageable);
    }

    public Optional<CompetidorModel> getCompetidorById(Long id){
        return this.competidorService.findById(id);
    }

    public CompetidorModel createCompetidor(CompetidorModel competidorModel){
        return this.competidorService.save(competidorModel);
    }

    public void deleteCompetidor(CompetidorModel competidorModel){
        this.competidorService.delete(competidorModel);
    }


    /** TROPEIROS **/
    public Page<TropeiroModel> getTropeiros(Pageable pageable){
        return this.tropeiroService.findAll(pageable);
    }

    public Optional<TropeiroModel> getTropeiroById(Long id){
        return this.tropeiroService.findById(id);
    }

    public TropeiroModel createTropeiro(TropeiroModel tropeiroModel){
        return this.tropeiroService.save(tropeiroModel);
    }

    public void deleteTropeiro(TropeiroModel tropeiroModel){
        this.tropeiroService.delete(tropeiroModel);
    }

    public boolean tropeiroExistsByNameAndSigla(String nome, String sigla){
        return this.tropeiroService.existsByNameAndSigla(nome, sigla);
    }


    /** ANIMAL **/
    public AnimalModel createAnimal(AnimalModel animalModel){
        return this.animalService.save(animalModel);
    }

    public Optional<AnimalModel> getAnimal(Long id){
        return this.animalService.findById(id);
    }

    public Page<AnimalModel> getAnimais(Pageable pageable){
        return this.animalService.findAll(pageable);
    }

    public void deleteAnimal(AnimalModel animalModel){
        TropeiroModel tropeiroModel = animalModel.getProprietario();

        if(tropeiroModel.getBoiada().size() < 2){
            this.tropeiroService.delete(tropeiroModel);
            return;
        }
        this.animalService.delete(animalModel);
    }

    public boolean animalExistsByNomeAndProprietario(String nome, TropeiroModel tropeiroModel){
        return this.animalService.existsByNomeAndProprietario(nome, tropeiroModel);
    }
}
