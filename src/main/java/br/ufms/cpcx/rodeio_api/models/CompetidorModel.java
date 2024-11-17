package br.ufms.cpcx.rodeio_api.models;

import br.ufms.cpcx.rodeio_api.dtos.LocalizacaoDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jdk.jfr.Event;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false, length = 64)
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

    @JsonIgnoreProperties("competidor")
    @OneToMany(mappedBy = "competidor")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<EventoCompetidorInscricaoModel> eventos;

    public void setEventos(List<EventoCompetidorInscricaoModel> eventos){
        this.eventos = new ArrayList<>(eventos);
    }
}
