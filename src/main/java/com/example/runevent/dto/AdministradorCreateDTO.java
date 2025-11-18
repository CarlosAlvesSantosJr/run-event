package com.example.runevent.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AdministradorCreateDTO {
    @NotBlank @Size(max = 100)
    private String nome;

    @Valid
    private LoginDTO login;

    public AdministradorCreateDTO() {}

    public String getNome()
    { return nome; }

    public void setNome(String nome)
    { this.nome = nome; }

    public LoginDTO getLogin()
    { return login; }

    public void setLogin(LoginDTO login)
    { this.login = login; }

}
