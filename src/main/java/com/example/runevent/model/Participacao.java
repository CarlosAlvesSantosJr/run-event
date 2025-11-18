package com.example.runevent.model;

import jakarta.persistence.*;


@Entity
@Table(name = "participacao")
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

    public Participacao() {
    }

    public Participacao(ParticipacaoId id, Corrida corrida, Atleta atleta) {
        this.id = id;
        this.corrida = corrida;
        this.atleta = atleta;
    }

    public ParticipacaoId getId() {
        return id;
    }

    public void setId(ParticipacaoId id) {
        this.id = id;
    }

    public Corrida getCorrida() {
        return corrida;
    }

    public void setCorrida(Corrida corrida) {
        this.corrida = corrida;
    }

    public Atleta getAtleta() {
        return atleta;
    }

    public void setAtleta(Atleta atleta) {
        this.atleta = atleta;
    }
}
