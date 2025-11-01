package com.example.runevent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "corrida")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
