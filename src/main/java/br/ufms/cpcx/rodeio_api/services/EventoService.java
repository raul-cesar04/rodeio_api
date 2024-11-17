package br.ufms.cpcx.rodeio_api.services;

import br.ufms.cpcx.rodeio_api.dtos.LocalizacaoDTO;
import br.ufms.cpcx.rodeio_api.models.EventoModel;
import br.ufms.cpcx.rodeio_api.repositories.EventoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EventoService {
    private final EventoRepository eventoRepository;
    protected EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @Transactional
    protected EventoModel save(EventoModel eventoModel) {
        return eventoRepository.save(eventoModel);
    }

    @Transactional
    protected void delete(EventoModel eventoModel) {
        eventoRepository.delete(eventoModel);
    }

    protected Optional<EventoModel> findById(Long id) {
        return eventoRepository.findById(id);
    }

    protected Page<EventoModel> findAll(Pageable pageable){
        return eventoRepository.findAll(pageable);
    }

    protected boolean existsByTituloAndLocalizacao(String titulo, LocalizacaoDTO localizacao) {
        return eventoRepository.existsByTituloAndLocalizacao(titulo, localizacao);
    }
}
