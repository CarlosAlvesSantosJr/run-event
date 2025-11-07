package com.example.runevent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


import java.time.LocalTime;

@Entity
@Table(name = "resultado")
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResultado;

    @ManyToOne
    @JoinColumn(name = "id_corrida", nullable = false)
    private Corrida corrida;

    @ManyToOne
    @JoinColumn(name = "id_atleta", nullable = false)
    private Atleta atleta;

    @NotNull
    private LocalTime tempo;

    @NotNull
    private Integer colocacaoGeral;

    @NotNull
    private Integer colocacaoCategoria;

    public Resultado() {
    }

    public Resultado(Long idResultado, Corrida corrida, Atleta atleta, LocalTime tempo, Integer colocacaoGeral, Integer colocacaoCategoria) {
        this.idResultado = idResultado;
        this.corrida = corrida;
        this.atleta = atleta;
        this.tempo = tempo;
        this.colocacaoGeral = colocacaoGeral;
        this.colocacaoCategoria = colocacaoCategoria;
    }

    public Long getIdResultado() {
        return idResultado;
    }

    public void setIdResultado(Long idResultado) {
        this.idResultado = idResultado;
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

    public @NotNull LocalTime getTempo() {
        return tempo;
    }

    public void setTempo(@NotNull LocalTime tempo) {
        this.tempo = tempo;
    }

    public @NotNull Integer getColocacaoGeral() {
        return colocacaoGeral;
    }

    public void setColocacaoGeral(@NotNull Integer colocacaoGeral) {
        this.colocacaoGeral = colocacaoGeral;
    }

    public @NotNull Integer getColocacaoCategoria() {
        return colocacaoCategoria;
    }

    public void setColocacaoCategoria(@NotNull Integer colocacaoCategoria) {
        this.colocacaoCategoria = colocacaoCategoria;
    }
}