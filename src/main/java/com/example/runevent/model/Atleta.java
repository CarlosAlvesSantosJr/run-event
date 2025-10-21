package com.example.runevent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
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
@Table(name = "atleta")
public class Atleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Past(message = "A data de nascimento deve ser no passado")
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento; // Tipo moderno

    @NotBlank
    @Size(max = 1)
    @Column(name = "sexo", nullable = false)
    private String sexo;

    @ManyToOne
    @JoinColumn(name = "categoria_id") // Define a coluna de junção
    private Categoria categoria;

    @OneToOne(cascade = CascadeType.ALL) // Adiciona o relacionamento 1:1 com Login
    @JoinColumn(name = "login_id", referencedColumnName = "id")
    @NotNull
    private Login login;

    @OneToMany(mappedBy = "atleta", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Resultado> resultados;
}
