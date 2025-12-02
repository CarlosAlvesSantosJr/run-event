package com.example.runevent.dto;

import com.example.runevent.model.Atleta;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AtletaCreateDTO {
    @NotBlank @Size(max = 100)
    private String nome;

    @NotNull
    private LocalDate dataNascimento;

    @NotNull
    private Atleta.Genero genero;

    @Valid
    private LoginDTO login;

    private Long idCategoria; // opcional

    public AtletaCreateDTO() {}

    public String getNome()
    { return nome; }

    public void setNome(String nome)
    { this.nome = nome; }

    public LocalDate getDataNascimento()
    { return dataNascimento; }

    public void setDataNascimento(LocalDate dataNascimento)
    { this.dataNascimento = dataNascimento; }

    public Atleta.Genero getGenero()
    { return genero; }

    public void setGenero(Atleta.Genero genero)
    { this.genero = genero; }

    public LoginDTO getLogin()
    { return login; }

    public void setLogin(LoginDTO login)
    { this.login = login; }

    public Long getIdCategoria()
    { return idCategoria; }

    public void setIdCategoria(Long idCategoria)
    { this.idCategoria = idCategoria; }

}
