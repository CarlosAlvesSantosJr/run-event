package com.example.runevent.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCategoria;
    private String faixaEtaria;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Atleta> atletas;

    @ManyToMany(mappedBy = "categorias")
    @ToString.Exclude
    private List<Corrida> corridas;

}
