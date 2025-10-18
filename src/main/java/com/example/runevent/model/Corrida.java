package com.example.runevent.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Corrida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCorrida;
    private Date dataCorrida;

    @ManyToMany
    @JoinTable(
            name = "corrida_categoria",
            joinColumns = @JoinColumn(name = "corrida_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    @ToString.Exclude
    private List<Categoria> categorias;

}
