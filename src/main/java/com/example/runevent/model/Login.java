package com.example.runevent.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Login {

    private String usuario ;
    private String senha;
    private String tipoUsuario;

}
