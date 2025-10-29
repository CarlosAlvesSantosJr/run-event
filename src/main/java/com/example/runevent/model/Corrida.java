package com.example.runevent.model;

import jakarta.persistence.*;
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

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false, length = 150)
    private String local;

    @Column(nullable = false, precision = 5, scale = 2)
    private Double distancia;

    @ManyToOne
    @JoinColumn(name = "id_adm", nullable = false)
    private Administrador adm;
}
