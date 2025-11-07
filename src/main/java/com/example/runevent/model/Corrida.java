package com.example.runevent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import java.time.LocalDate;

@Entity
@Table(name = "corrida")
public class Corrida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCorrida;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nome;

    @NotNull
    @Column(nullable = false)
    private LocalDate data;

    @NotBlank
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String local;

    @NotNull
    @Column(nullable = false)
    private Double distancia;

    @ManyToOne
    @JoinColumn(name = "id_adm", nullable = false)
    private Administrador administrador;

    public Corrida() {
    }

    public Corrida(Long idCorrida, String nome, LocalDate data, String local, Double distancia, Administrador administrador) {
        this.idCorrida = idCorrida;
        this.nome = nome;
        this.data = data;
        this.local = local;
        this.distancia = distancia;
        this.administrador = administrador;
    }

    public Long getIdCorrida() {
        return idCorrida;
    }

    public void setIdCorrida(Long idCorrida) {
        this.idCorrida = idCorrida;
    }

    public @NotBlank @Size(max = 100) String getNome() {
        return nome;
    }

    public void setNome(@NotBlank @Size(max = 100) String nome) {
        this.nome = nome;
    }

    public @NotNull LocalDate getData() {
        return data;
    }

    public void setData(@NotNull LocalDate data) {
        this.data = data;
    }

    public @NotBlank @Size(max = 150) String getLocal() {
        return local;
    }

    public void setLocal(@NotBlank @Size(max = 150) String local) {
        this.local = local;
    }

    public @NotNull Double getDistancia() {
        return distancia;
    }

    public void setDistancia(@NotNull Double distancia) {
        this.distancia = distancia;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }
}
