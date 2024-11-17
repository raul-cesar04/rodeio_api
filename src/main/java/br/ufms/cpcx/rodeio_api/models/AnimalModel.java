package br.ufms.cpcx.rodeio_api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(
        name = "TB_ANIMAL",
        uniqueConstraints = {
                @UniqueConstraint(name = "TB_ANIMAL_UQ", columnNames = {
                        "nome",
                        "proprietario"
                })
        }
)
@Data
@NoArgsConstructor
public class AnimalModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 64)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 6)
    private AnimalLadoBreteEnum ladoBrete;

    @JsonIgnoreProperties("boiada")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tropeiro", foreignKey = @ForeignKey(name = "FK_PROPRIETARIO"), nullable = false)
    private TropeiroModel proprietario;
}
