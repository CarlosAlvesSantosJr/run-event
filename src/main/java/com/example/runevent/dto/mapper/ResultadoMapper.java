package com.example.runevent.dto.mapper;

import com.example.runevent.dto.ResultadoDTO;
import com.example.runevent.model.Atleta;
import com.example.runevent.model.Corrida;
import com.example.runevent.model.Resultado;

public class ResultadoMapper {

    public static Resultado toEntity(ResultadoDTO dto, Corrida corrida, Atleta atleta) {
        if (dto == null) return null;

        Resultado resultado = new Resultado();
        resultado.setCorrida(corrida);
        resultado.setAtleta(atleta);
        resultado.setTempo(dto.getTempo());
        resultado.setColocacaoGeral(dto.getColocacaoGeral());
        resultado.setColocacaoCategoria(dto.getColocacaoCategoria());
        return resultado;
    }
}