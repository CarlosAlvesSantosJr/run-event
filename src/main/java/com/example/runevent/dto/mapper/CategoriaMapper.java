package com.example.runevent.dto.mapper;

import com.example.runevent.dto.CategoriaDTO;
import com.example.runevent.model.Categoria;

public class CategoriaMapper {

    public static Categoria toEntity(CategoriaDTO dto) {
        if (dto == null) return null;

        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());
        return categoria;
    }

    public static CategoriaDTO toDTO(Categoria entity) {
        if (entity == null) return null;

        CategoriaDTO dto = new CategoriaDTO();
        dto.setNome(entity.getNome());
        dto.setDescricao(entity.getDescricao());
        return dto;
    }
}