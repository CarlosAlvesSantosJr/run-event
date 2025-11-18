package com.example.runevent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "login")
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLogin;

    @Email
    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank
    @Size(min = 6, max = 100)
    @Column(nullable = false, length = 100)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TipoUsuario tipo; // ADM ou ATLETA

    public enum TipoUsuario {
        ADM,
        ATLETA
    }

    public Login() {
    }

    public Login(Long idLogin, String email, String senha, TipoUsuario tipo) {
        this.idLogin = idLogin;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }

    public Long getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(Long idLogin) {
        this.idLogin = idLogin;
    }

    public @Email @NotBlank @Size(max = 100) String getEmail() {
        return email;
    }

    public void setEmail(@Email @NotBlank @Size(max = 100) String email) {
        this.email = email;
    }

    public @NotBlank @Size(min = 6, max = 100) String getSenha() {
        return senha;
    }

    public void setSenha(@NotBlank @Size(min = 6, max = 100) String senha) {
        this.senha = senha;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }
}
