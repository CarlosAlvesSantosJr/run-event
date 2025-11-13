package com.example.runevent.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoriaDTO {
    @NotBlank @Size(max = 50)
    private String nome;

    @Size(max = 255)
    private String descricao;

    public CategoriaDTO() {}

    public String getNome()
    { return nome; }

    public void setNome(String nome)
    { this.nome = nome; }

    public String getDescricao()
    { return descricao; }

    public void setDescricao(String descricao)
    { this.descricao = descricao; }

}
