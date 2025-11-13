package com.example.runevent.controller;

import com.example.runevent.model.Atleta;
import com.example.runevent.model.Corrida;
import com.example.runevent.model.Resultado;
import com.example.runevent.service.ResultadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("removal")
@WebMvcTest(controllers = ResultadoController.class)
class ResultadoControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private ResultadoService resultadoService;
    @Autowired private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/resultados cria resultado e retorna 201")
    void deveCriarResultado() throws Exception {
        Resultado resultado = new Resultado(1L, new Corrida(), new Atleta(),
                LocalTime.of(0,45,30), 1, 1);

        when(resultadoService.create(any(Resultado.class), eq(1L), eq(2L))).thenReturn(resultado);

        String payload = """
            {
              "tempo": "00:45:30",
              "colocacaoGeral": 1,
              "colocacaoCategoria": 1
            }
            """;

        mockMvc.perform(post("/api/resultados?idAtleta=1&idCorrida=2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/resultados/1"));
    }

    @Test
    @DisplayName("GET /api/resultados/corrida/{id} retorna lista de resultados")
    void deveListarPorCorrida() throws Exception {
        Resultado r = new Resultado(1L, new Corrida(), new Atleta(),
                LocalTime.of(0,50,10), 2, 1);

        when(resultadoService.findByCorrida(10L)).thenReturn(List.of(r));

        mockMvc.perform(get("/api/resultados/corrida/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].colocacaoGeral").value(2));
    }

    @Test
    @DisplayName("POST /api/resultados deve retornar 400 se corrida ou atleta inválido")
    void deveRetornar400QuandoErroDeCriacao() throws Exception {
        when(resultadoService.create(any(Resultado.class), eq(1L), eq(2L)))
                .thenThrow(new RuntimeException("Erro ao salvar resultado"));

        String payload = """
        {
          "tempo": "00:45:30",
          "colocacaoGeral": 1,
          "colocacaoCategoria": 1
        }
        """;

        mockMvc.perform(post("/api/resultados?idAtleta=1&idCorrida=2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Erro ao salvar resultado"));
    }

}
