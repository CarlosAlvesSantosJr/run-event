package com.example.runevent.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Resultado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Time tempoProva;
    private int posicaoG;
    private int posicaoC;

    @ManyToOne
    @JoinColumn(name = "corrida_id")
    private Corrida corrida;

    @ManyToOne
    @JoinColumn(name = "atleta_id")
    private Atleta atleta;
}
