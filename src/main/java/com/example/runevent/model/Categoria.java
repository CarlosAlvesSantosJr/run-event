package com.example.runevent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(name = "nome_categoria", nullable = false)
    private String nomeCategoria;

    @Size(max = 20)
    @Column(name = "faixa_etaria")
    private String faixaEtaria;

    @OneToMany(mappedBy = "categoria") // Não precisa de Cascade aqui, o Atleta gerencia isso
    @ToString.Exclude
    private List<Atleta> atletas;

    @ManyToMany(mappedBy = "categorias") // Contraparte do ManyToMany em Corrida
    @ToString.Exclude
    private List<Corrida> corridas;
}