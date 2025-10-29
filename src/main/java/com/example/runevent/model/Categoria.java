package com.example.runevent.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "categoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategoria;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(length = 255)
    private String descricao;
}
