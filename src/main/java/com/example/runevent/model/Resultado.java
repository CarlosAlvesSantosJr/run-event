package com.example.runevent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalTime; // Alterado de Time para LocalTime

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "resultado")
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tempo_prova")
    private LocalTime tempoProva; // Tipo moderno

    @Positive
    @Column(name = "posicaog")
    private int posicaoG;

    @Positive
    @Column(name = "posicaoc")
    private int posicaoC;

    @ManyToOne
    @JoinColumn(name = "corrida_id", nullable = false) // Coluna de junção
    private Corrida corrida;

    @ManyToOne
    @JoinColumn(name = "atleta_id", nullable = false) // Coluna de junção
    private Atleta atleta;
}