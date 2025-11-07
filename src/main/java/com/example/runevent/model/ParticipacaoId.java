package com.example.runevent.model;

import jakarta.persistence.Embeddable;


import java.io.Serializable;

@Embeddable
public class ParticipacaoId implements Serializable {
    private Long idCorrida;
    private Long idAtleta;

    public ParticipacaoId() {
    }

    public ParticipacaoId(Long idCorrida, Long idAtleta) {
        this.idCorrida = idCorrida;
        this.idAtleta = idAtleta;
    }

    public Long getIdCorrida() {
        return idCorrida;
    }

    public void setIdCorrida(Long idCorrida) {
        this.idCorrida = idCorrida;
    }

    public Long getIdAtleta() {
        return idAtleta;
    }

    public void setIdAtleta(Long idAtleta) {
        this.idAtleta = idAtleta;
    }
}
