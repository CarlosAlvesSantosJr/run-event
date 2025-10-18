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
public class Administrador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String administrador;

    @OneToMany(mappedBy = "administrador", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Corrida> corridas;
}
