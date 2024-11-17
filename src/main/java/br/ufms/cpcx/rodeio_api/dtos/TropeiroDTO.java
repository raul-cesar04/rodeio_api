package br.ufms.cpcx.rodeio_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TropeiroDTO {
    private Long id;

    @NotBlank
    @Size(max = 64)
    private String nome;

    @NotBlank
    @Size(max = 5)
    private String sigla;

    private List<AnimalDTO> boiada;
}
