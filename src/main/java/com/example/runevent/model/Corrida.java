package com.example.runevent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate; // Alterado de Date para LocalDate
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "corrida")
public class Corrida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "nome_corrida", nullable = false)
    private String nomeCorrida;

    @NotNull
    @Future(message = "A data da corrida deve ser no futuro")
    @Column(name = "data_corrida", nullable = false)
    private LocalDate dataCorrida; // Tipo moderno

    @ManyToOne // Adicionado relacionamento N:1 com Administrador
    @JoinColumn(name = "administrador_id")
    @NotNull
    private Administrador administrador;

    @ManyToMany // Mantido o ManyToMany que você criou
    @JoinTable(
            name = "corrida_categoria", // Nome da tabela de junção
            joinColumns = @JoinColumn(name = "corrida_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    @ToString.Exclude
    private List<Categoria> categorias;

    @OneToMany(mappedBy = "corrida", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Resultado> resultados;
}