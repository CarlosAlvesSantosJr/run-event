package com.example.runevent.controller;

import com.example.runevent.model.Corrida;
import com.example.runevent.service.CorridaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("removal")
@WebMvcTest(controllers = CorridaController.class)
class CorridaControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private CorridaService corridaService;
    @Autowired private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/corridas cria corrida e retorna 201")
    void deveCriarCorrida() throws Exception {
        Corrida corrida = new Corrida(1L, "Corrida Run", LocalDate.of(2025,1,1), "SP", 10.0, null);
        when(corridaService.create(any(Corrida.class), eq(1L))).thenReturn(corrida);

        String payload = """
            {
              "nome": "Corrida Run",
              "data": "2025-01-01",
              "local": "SP",
              "distancia": 10.0
            }
            """;

        mockMvc.perform(post("/api/corridas?idAdm=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/corridas/1"))
                .andExpect(jsonPath("$.nome").value("Corrida Run"));
    }

    @Test
    @DisplayName("GET /api/corridas retorna lista 200")
    void deveListarCorridas() throws Exception {
        when(corridaService.findAll()).thenReturn(List.of(new Corrida(1L, "Maratona SP", LocalDate.now(), "SP", 42.0, null)));

        mockMvc.perform(get("/api/corridas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Maratona SP"));
    }

    @Test
    @DisplayName("DELETE /api/corridas/{id} retorna 204")
    void deveDeletarCorrida() throws Exception {
        doNothing().when(corridaService).delete(1L);

        mockMvc.perform(delete("/api/corridas/1"))
                .andExpect(status().isNoContent());

        verify(corridaService).delete(1L);
    }

    @Test
    @DisplayName("POST /api/corridas deve retornar 400 se adm inválido")
    void deveRetornar400QuandoAdmInvalido() throws Exception {
        when(corridaService.create(any(Corrida.class), eq(1L)))
                .thenThrow(new RuntimeException("Administrador não encontrado"));

        String payload = """
        {
          "nome": "Corrida Teste",
          "data": "2025-05-01",
          "local": "SP",
          "distancia": 10.0
        }
        """;

        mockMvc.perform(post("/api/corridas?idAdm=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Administrador não encontrado"));
    }

}
