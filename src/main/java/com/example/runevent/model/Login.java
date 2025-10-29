package com.example.runevent.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "login")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLogin;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TipoUsuario tipo; // ADM ou ATLETA

    public enum TipoUsuario {
        ADM,
        ATLETA
    }
}
