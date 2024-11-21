package br.ufms.cpcx.rodeio_api.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventoCompetidorInscricaoDTO {
    private CompetidorDTO competidor;
    private EventoDTO evento;
    private Float pontuacao;
}
