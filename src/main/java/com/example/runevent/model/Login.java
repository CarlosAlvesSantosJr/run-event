package com.example.runevent.model;

import com.example.runevent.enums.TipoUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "login") // Especifica o nome da tabela
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O usuário não pode estar em branco")
    @Size(max = 50)
    @Column(name = "usuario", unique = true, nullable = false)
    private String usuario;

    @NotBlank(message = "A senha não pode estar em branco")
    @Size(min = 6, max = 100) // Assumindo que a senha será armazenada com hash
    @Column(name = "senha", nullable = false)
    private String senha;

    @NotNull(message = "O tipo de usuário é obrigatório")
    @Enumerated(EnumType.STRING) // Salva o Enum como "ATLETA" ou "ADMINISTRADOR" no banco
    @Column(name = "tipo_usuario", nullable = false)
    private TipoUsuario tipoUsuario;
}
