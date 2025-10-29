package com.example.runevent.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "atleta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Atleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAtleta;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Genero genero;

    @OneToOne
    @JoinColumn(name = "id_login", unique = true)
    private Login login;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    public enum Genero {
        M, F, OUTRO
    }
}
