package com.example.runevent.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class CorridaCreateDTO {
    @NotBlank @Size(max = 100)
    private String nome;

    @NotNull
    private LocalDate data;

    @NotBlank @Size(max = 150)
    private String local;

    @NotNull
    private Double distancia;

    public CorridaCreateDTO() {}

    public String getNome()
    { return nome; }

    public void setNome(String nome)
    { this.nome = nome; }

    public LocalDate getData()
    { return data; }

    public void setData(LocalDate data)
    { this.data = data; }

    public String getLocal()
    { return local; }

    public void setLocal(String local)
    { this.local = local; }

    public Double getDistancia()
    { return distancia; }

    public void setDistancia(Double distancia)
    { this.distancia = distancia; }

}
