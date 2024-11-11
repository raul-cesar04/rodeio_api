package br.ufms.cpcx.rodeio_api.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CompetidorDTO {
    @NotBlank
    @Size(max=100)
    private String nome;

    @NotNull
    private Integer idade;

    @NotNull
    @Valid
    private LocalizacaoDTO cidadeNatal;
}
