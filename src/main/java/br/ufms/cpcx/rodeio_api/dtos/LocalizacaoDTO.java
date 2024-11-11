package br.ufms.cpcx.rodeio_api.dtos;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class LocalizacaoDTO {
    @NotBlank
    @NotNull
    @Size(max = 64)
    private String cidade;
    @NotNull
    EstadoEnum estado;
}
