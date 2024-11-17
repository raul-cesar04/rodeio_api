package br.ufms.cpcx.rodeio_api.repositories;

import br.ufms.cpcx.rodeio_api.models.AnimalModel;
import br.ufms.cpcx.rodeio_api.models.TropeiroModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<AnimalModel, Long> {
    boolean existsByNomeAndProprietario(String nome, TropeiroModel proprietario);
}
