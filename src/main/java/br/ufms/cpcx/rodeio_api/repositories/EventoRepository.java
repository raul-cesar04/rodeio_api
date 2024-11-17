package br.ufms.cpcx.rodeio_api.repositories;

import br.ufms.cpcx.rodeio_api.dtos.LocalizacaoDTO;
import br.ufms.cpcx.rodeio_api.models.EventoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<EventoModel, Long> {
    boolean existsByTituloAndLocalizacao(String titulo, LocalizacaoDTO localizacao);
}
