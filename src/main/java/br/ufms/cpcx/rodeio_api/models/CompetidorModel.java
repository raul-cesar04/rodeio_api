package br.ufms.cpcx.rodeio_api.models;

import br.ufms.cpcx.rodeio_api.dtos.LocalizacaoDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(
        name = "TB_COMPETIDOR",
        uniqueConstraints = {
                @UniqueConstraint(name = "TB_COMPETIDOR_UQ", columnNames = {
                        "nome",
                        "cidade_natal",
                        "estado_natal"
                })
        }
)
@Data
@NoArgsConstructor
public class CompetidorModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private Integer idade;

    @Column(nullable = false)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "cidade", column = @Column(name = "cidade_natal")),
            @AttributeOverride(name = "estado", column = @Column(name = "estado_natal"))
    })
    private LocalizacaoDTO cidadeNatal;
}
