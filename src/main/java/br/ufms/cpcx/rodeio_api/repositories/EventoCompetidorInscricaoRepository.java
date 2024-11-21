package br.ufms.cpcx.rodeio_api.repositories;

import br.ufms.cpcx.rodeio_api.models.CompetidorModel;
import br.ufms.cpcx.rodeio_api.models.EventoCompetidorInscricaoModel;
import br.ufms.cpcx.rodeio_api.models.EventoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventoCompetidorInscricaoRepository extends JpaRepository<EventoCompetidorInscricaoModel, Long> {
    Optional<EventoCompetidorInscricaoModel> findByCompetidorAndEvento(CompetidorModel competidor, EventoModel evento);
}
