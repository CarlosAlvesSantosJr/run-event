package com.example.runevent.dto.mapper;

import com.example.runevent.dto.CorridaCreateDTO;
import com.example.runevent.model.Administrador;
import com.example.runevent.model.Corrida;

public class CorridaMapper {

    public static Corrida toEntity(CorridaCreateDTO dto, Administrador administrador) {
        if (dto == null) return null;

        Corrida corrida = new Corrida();
        corrida.setNome(dto.getNome());
        corrida.setData(dto.getData());
        corrida.setLocal(dto.getLocal());
        corrida.setDistancia(dto.getDistancia());
        corrida.setAdministrador(administrador);
        return corrida;
    }
}