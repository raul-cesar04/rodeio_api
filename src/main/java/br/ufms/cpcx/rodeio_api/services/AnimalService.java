package br.ufms.cpcx.rodeio_api.services;

import br.ufms.cpcx.rodeio_api.models.AnimalModel;
import br.ufms.cpcx.rodeio_api.models.TropeiroModel;
import br.ufms.cpcx.rodeio_api.repositories.AnimalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;
    protected AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Transactional
    protected AnimalModel save(AnimalModel animalModel){
        return animalRepository.save(animalModel);
    }

    @Transactional
    protected void delete(AnimalModel animalModel){
        animalRepository.delete(animalModel);
    }

    protected Optional<AnimalModel> findById(Long id){
        return animalRepository.findById(id);
    }

    protected Page<AnimalModel> findAll(Pageable pageable){
        return animalRepository.findAll(pageable);
    }

    protected boolean existsByNomeAndProprietario(String nome, TropeiroModel proprietario){
        return animalRepository.existsByNomeAndProprietario(nome, proprietario);
    }
}
