package com.example.runevent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategoria;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String nome;

    @Size(max = 255)
    @Column(length = 255)
    private String descricao;

    public Categoria() {
    }

    public Categoria(Long idCategoria, String nome, String descricao) {
        this.idCategoria = idCategoria;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public @NotBlank @Size(max = 50) String getNome() {
        return nome;
    }

    public void setNome(@NotBlank @Size(max = 50) String nome) {
        this.nome = nome;
    }

    public @Size(max = 255) String getDescricao() {
        return descricao;
    }

    public void setDescricao(@Size(max = 255) String descricao) {
        this.descricao = descricao;
    }
}
