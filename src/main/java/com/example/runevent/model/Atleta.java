package com.example.runevent.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Atleta {

    private String nome;
    private Date dataNascimento;
    private String sexo;
}
