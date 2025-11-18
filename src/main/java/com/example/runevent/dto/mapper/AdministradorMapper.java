package com.example.runevent.dto.mapper;

import com.example.runevent.dto.AdministradorCreateDTO;
import com.example.runevent.model.Administrador;

public class AdministradorMapper {

    public static Administrador toEntity(AdministradorCreateDTO dto) {
        if (dto == null) return null;

        Administrador administrador = new Administrador();
        administrador.setNome(dto.getNome());
        administrador.setLogin(LoginMapper.toEntity(dto.getLogin()));
        return administrador;
    }
}