package br.ufms.cpcx.rodeio_api.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(
        name = "TB_EVENTO_COMPETIDOR_INSCRICAO",
        uniqueConstraints = {
                @UniqueConstraint(name = "TB_EVENTO_COMPETIDOR_INSCRICAO_UQ", columnNames = {
                    "competidor", "evento"
                })
        }
)
@Data
@NoArgsConstructor
public class EventoCompetidorInscricaoModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("eventos")
    @JoinColumn(name = "competidor", nullable = false, foreignKey = @ForeignKey(name = "FK_COMPETIDOR"))
    private CompetidorModel competidor;

    @ManyToOne
    @JsonIgnoreProperties("competidores")
    @JoinColumn(name = "evento", nullable = false, foreignKey = @ForeignKey(name = "FK_EVENTO"))
    private EventoModel evento;

    @Column(nullable = false)
    private float pontuacao;
}
