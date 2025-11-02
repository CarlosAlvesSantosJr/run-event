package com.example.runevent.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "participacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Participacao {

    @EmbeddedId
    private ParticipacaoId id;

    @ManyToOne
    @MapsId("idCorrida")
    @JoinColumn(name = "id_corrida")
    private Corrida corrida;

    @ManyToOne
    @MapsId("idAtleta")
    @JoinColumn(name = "id_atleta")
    private Atleta atleta;
}
