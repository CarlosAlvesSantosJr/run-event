package com.example.runevent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import java.time.LocalDate;

@Entity
@Table(name = "atleta")
public class Atleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAtleta;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nome;

    @NotNull
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Genero genero;

    @OneToOne
    @JoinColumn(name = "id_login", nullable = false, unique = true)
    private Login login;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    public enum Genero {
        M, F, OUTRO
    }

    public Atleta() {
    }

    public Atleta(Long idAtleta, String nome, LocalDate dataNascimento, Genero genero, Login login, Categoria categoria) {
        this.idAtleta = idAtleta;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.login = login;
        this.categoria = categoria;
    }

    public Long getIdAtleta() {
        return idAtleta;
    }

    public void setIdAtleta(Long idAtleta) {
        this.idAtleta = idAtleta;
    }

    public @NotBlank @Size(max = 100) String getNome() {
        return nome;
    }

    public void setNome(@NotBlank @Size(max = 100) String nome) {
        this.nome = nome;
    }

    public @NotNull LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(@NotNull LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public @NotNull Genero getGenero() {
        return genero;
    }

    public void setGenero(@NotNull Genero genero) {
        this.genero = genero;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
