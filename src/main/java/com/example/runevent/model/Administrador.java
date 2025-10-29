package com.example.runevent.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "adm")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAdm;

    @Column(nullable = false, length = 100)
    private String nome;

    @OneToOne
    @JoinColumn(name = "id_login", nullable = false, unique = true)
    private Login login;
}
