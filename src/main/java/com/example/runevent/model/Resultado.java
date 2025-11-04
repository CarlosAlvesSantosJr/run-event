package com.example.runevent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "resultado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
