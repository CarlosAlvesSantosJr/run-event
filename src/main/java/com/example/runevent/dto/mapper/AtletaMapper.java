package com.example.runevent.dto.mapper;

import com.example.runevent.dto.AtletaCreateDTO;
import com.example.runevent.model.Atleta;
import com.example.runevent.model.Categoria;

public class AtletaMapper {

    public static Atleta toEntity(AtletaCreateDTO dto, Categoria categoria) {
        if (dto == null) return null;

        Atleta atleta = new Atleta();
        atleta.setNome(dto.getNome());
        atleta.setDataNascimento(dto.getDataNascimento());
        atleta.setGenero(dto.getGenero());
        atleta.setLogin(LoginMapper.toEntity(dto.getLogin()));
        atleta.setCategoria(categoria); // categoria buscada no service
        return atleta;
    }
}