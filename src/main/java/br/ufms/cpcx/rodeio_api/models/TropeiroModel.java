package br.ufms.cpcx.rodeio_api.models;

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

    @JsonIgnoreProperties("proprietario")
    @OneToMany(mappedBy = "proprietario", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<AnimalModel> boiada;

    public void setBoiada(List<AnimalModel> items){
        this.boiada = new ArrayList<>(items);
    }
}
