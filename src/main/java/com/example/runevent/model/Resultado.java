package com.example.runevent.model;

import lombok.*;

import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Resultado {

    private Time tempoProva;
    private int posicaoG;
    private int posicaoC;
}
