package br.ufms.cpcx.rodeio_api.services;

import br.ufms.cpcx.rodeio_api.models.TropeiroModel;
import br.ufms.cpcx.rodeio_api.repositories.TropeiroRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TropeiroService {
    private final TropeiroRepository tropeiroRepository;

    protected TropeiroService(TropeiroRepository tropeiroRepository) {
        this.tropeiroRepository = tropeiroRepository;
    }

    @Transactional
    protected TropeiroModel save(TropeiroModel tropeiroModel){
        return tropeiroRepository.save(tropeiroModel);
    }

    @Transactional
    protected void delete(TropeiroModel tropeiroModel){
        tropeiroRepository.delete(tropeiroModel);
    }

    protected Optional<TropeiroModel> findById(Long id){
        return tropeiroRepository.findById(id);
    }

    protected Page<TropeiroModel> findAll(Pageable pageable){
        return tropeiroRepository.findAll(pageable);
    }

    protected boolean existsByNameAndSigla(String name, String sigla){
        return tropeiroRepository.existsByNomeAndSigla(name, sigla);
    }
}
