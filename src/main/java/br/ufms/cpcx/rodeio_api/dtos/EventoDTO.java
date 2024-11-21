package br.ufms.cpcx.rodeio_api.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EventoDTO {
    private Long id;
    @NotBlank
    @Size(max = 100)
    private String titulo;

    @Valid
    private LocalizacaoDTO localizacao;

    private List<EventoCompetidorInscricaoDTO> competidores;
}
