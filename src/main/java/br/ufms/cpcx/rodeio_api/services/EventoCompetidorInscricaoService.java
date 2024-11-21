package br.ufms.cpcx.rodeio_api.services;

import br.ufms.cpcx.rodeio_api.models.CompetidorModel;
import br.ufms.cpcx.rodeio_api.models.EventoCompetidorInscricaoModel;
import br.ufms.cpcx.rodeio_api.models.EventoModel;
import br.ufms.cpcx.rodeio_api.repositories.EventoCompetidorInscricaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EventoCompetidorInscricaoService {
    private final EventoCompetidorInscricaoRepository eventoCompetidorInscricaoRepository;

    public EventoCompetidorInscricaoService(EventoCompetidorInscricaoRepository eventoCompetidorInscricaoRepository) {
        this.eventoCompetidorInscricaoRepository = eventoCompetidorInscricaoRepository;
    }

    @Transactional
    protected EventoCompetidorInscricaoModel save(EventoCompetidorInscricaoModel model) {
        return eventoCompetidorInscricaoRepository.save(model);
    }

    @Transactional
    protected void delete(EventoCompetidorInscricaoModel model) {
        eventoCompetidorInscricaoRepository.delete(model);
    }

    protected Optional<EventoCompetidorInscricaoModel> findById(Long id) {
        return eventoCompetidorInscricaoRepository.findById(id);
    }

        protected Optional<EventoCompetidorInscricaoModel> findByCompetidorAndEvento(CompetidorModel competidor, EventoModel evento){
        return eventoCompetidorInscricaoRepository.findByCompetidorAndEvento(competidor, evento);
    }
}
