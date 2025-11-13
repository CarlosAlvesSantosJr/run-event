package com.example.runevent.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginDTO {
    @Email @NotBlank @Size(max = 100)
    private String email;

    @NotBlank @Size(min = 6, max = 100)
    private String senha;

    public LoginDTO() {}

    public LoginDTO(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getEmail()
    { return email; }

    public void setEmail(String email)
    { this.email = email; }

    public String getSenha()
    { return senha; }

    public void setSenha(String senha)
    { this.senha = senha; }

}
