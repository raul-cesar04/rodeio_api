package br.ufms.cpcx.rodeio_api.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CompetidorDTO {
    private Long id;
    @NotBlank
    @Size(max=100)
    private String nome;

    @NotNull
    private Integer idade;

    @NotNull
    @Valid
    private LocalizacaoDTO cidadeNatal;

    private List<EventoCompetidorInscricaoDTO> eventos;
}
