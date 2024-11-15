package br.ufms.cpcx.rodeio_api.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(
        name = "TB_TROPEIRO",
        uniqueConstraints = {
                @UniqueConstraint(name = "TB_TROPEIRO_UQ", columnNames = {
                        "nome",
                        "sigla"
                })
        }
)
@Data
@NoArgsConstructor
public class TropeiroModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 64)
    private String nome;

    @Column(nullable = false, length = 5)
    private String sigla;

    // TODO: Relacionamento com Animal

}
