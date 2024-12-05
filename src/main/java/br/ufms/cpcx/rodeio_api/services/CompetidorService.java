package br.ufms.cpcx.rodeio_api.services;

import br.ufms.cpcx.rodeio_api.models.CompetidorModel;
import br.ufms.cpcx.rodeio_api.repositories.CompetidorRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
class CompetidorService {
    private final CompetidorRepository competidorRepository;
    protected CompetidorService(CompetidorRepository competidorRepository) {
        this.competidorRepository = competidorRepository;
    }

    @Transactional
    protected CompetidorModel save(CompetidorModel competidorModel) {
        return competidorRepository.save(competidorModel);
    }

    @Transactional
    protected void delete(CompetidorModel competidorModel) {
        competidorRepository.delete(competidorModel);
    }

    protected Page<CompetidorModel> findAll(Pageable pageable){
        return competidorRepository.findAll(pageable);
    }

    protected Optional<CompetidorModel> findById(Long id){
       return competidorRepository.findById(id);
    }

    protected Page<CompetidorModel> findByName(Pageable pageable, String name){
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("nome", matcher->matcher.startsWith())
                .withIgnoreCase()
                .withIgnoreNullValues();

        CompetidorModel competidorModel = new CompetidorModel();
        competidorModel.setNome(name);
        var example = Example.of(competidorModel, exampleMatcher);

        return competidorRepository.findAll(example, pageable);
    }
}
