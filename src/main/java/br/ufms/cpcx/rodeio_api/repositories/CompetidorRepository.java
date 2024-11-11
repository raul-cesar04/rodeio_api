package br.ufms.cpcx.rodeio_api.repositories;

import br.ufms.cpcx.rodeio_api.models.CompetidorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetidorRepository extends JpaRepository<CompetidorModel, Long> {
}
