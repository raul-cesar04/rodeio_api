package br.ufms.cpcx.rodeio_api.services;

import br.ufms.cpcx.rodeio_api.models.CompetidorModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RodeioApiServiceFacade {
    private final CompetidorService competidorService;
    public RodeioApiServiceFacade(CompetidorService ccompetidorService) {
        this.competidorService = ccompetidorService;
    }

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
}
