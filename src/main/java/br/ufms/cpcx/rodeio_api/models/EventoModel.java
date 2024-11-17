package br.ufms.cpcx.rodeio_api.models;

import br.ufms.cpcx.rodeio_api.dtos.LocalizacaoDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "TB_EVENTO",
        uniqueConstraints = {
                @UniqueConstraint(name = "TB_EVENTO_UQ", columnNames = {
                        "titulo",
                        "localizacao_cidade",
                        "localizacao_estado"
                })
        }
)
@Data
@NoArgsConstructor
public class EventoModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="cidade", column = @Column(name = "localizacao_cidade")),
            @AttributeOverride(name="estado", column = @Column(name = "localizacao_estado"))
    })
    private LocalizacaoDTO localizacao;

    @JsonIgnoreProperties("evento")
    @OneToMany(mappedBy = "evento")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<EventoCompetidorInscricaoModel> competidores;

    public void setCompetidores(List<EventoCompetidorInscricaoModel> competidores) {
        this.competidores = new ArrayList<>(competidores);
    }
}
