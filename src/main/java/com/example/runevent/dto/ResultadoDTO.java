package com.example.runevent.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public class ResultadoDTO {
    @NotNull
    private LocalTime tempo;

    @NotNull
    private Integer colocacaoGeral;

    @NotNull
    private Integer colocacaoCategoria;

    public ResultadoDTO() {}

    public LocalTime getTempo()
    { return tempo; }

    public void setTempo(LocalTime tempo)
    { this.tempo = tempo; }

    public Integer getColocacaoGeral()
    { return colocacaoGeral; }

    public void setColocacaoGeral(Integer colocacaoGeral)
    { this.colocacaoGeral = colocacaoGeral; }

    public Integer getColocacaoCategoria()
    { return colocacaoCategoria; }

    public void setColocacaoCategoria(Integer colocacaoCategoria)
    { this.colocacaoCategoria = colocacaoCategoria; }

}
