package br.ufms.cpcx.rodeio_api.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnimalDTO {
    @NotBlank
    @Size(max = 64)
    private String nome;

    @Valid
    private TropeiroDTO proprietario;
}
