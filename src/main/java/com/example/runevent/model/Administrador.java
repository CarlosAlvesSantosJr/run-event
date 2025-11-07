package com.example.runevent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "administrador")
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAdm;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nome;

    @OneToOne
    @JoinColumn(name = "id_login", nullable = false, unique = true)
    private Login login;

    public Administrador() {
    }

    public Administrador(Long idAdm, String nome, Login login) {
        this.idAdm = idAdm;
        this.nome = nome;
        this.login = login;
    }

    public Long getIdAdm() {
        return idAdm;
    }

    public void setIdAdm(Long idAdm) {
        this.idAdm = idAdm;
    }

    public @NotBlank @Size(max = 100) String getNome() {
        return nome;
    }

    public void setNome(@NotBlank @Size(max = 100) String nome) {
        this.nome = nome;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
}
