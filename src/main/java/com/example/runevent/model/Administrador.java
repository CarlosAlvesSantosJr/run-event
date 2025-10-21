package com.example.runevent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "administrador")
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "nome", nullable = false) // Corrigido de "administrador" para "nome"
    private String nome;

    @OneToOne(cascade = CascadeType.ALL) // Adiciona o relacionamento 1:1 com Login
    @JoinColumn(name = "login_id", referencedColumnName = "id")
    @NotNull
    private Login login;

    @OneToMany(mappedBy = "administrador", cascade = CascadeType.ALL) // mappedBy correto
    @ToString.Exclude
    private List<Corrida> corridas;
}
