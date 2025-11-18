package com.example.runevent.dto.mapper;

import com.example.runevent.dto.LoginDTO;
import com.example.runevent.model.Login;

public class LoginMapper {

    public static Login toEntity(LoginDTO dto) {
        if (dto == null) return null;

        Login login = new Login();
        login.setEmail(dto.getEmail());
        login.setSenha(dto.getSenha());
        // tipo será definido no serviço (ADM ou ATLETA)
        return login;
    }

    public static LoginDTO toDTO(Login entity) {
        if (entity == null) return null;

        return new LoginDTO(
                entity.getEmail(),
                entity.getSenha()
        );
    }
}