package com.example.runevent.controller;

import com.example.runevent.model.Participacao;
import com.example.runevent.service.ParticipacaoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("removal")
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = ParticipacaoController.class)
class ParticipacaoControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private ParticipacaoService participacaoService;

    @Test
    @DisplayName("POST /api/participacoes inscreve atleta e retorna 201")
    void deveCriarParticipacao() throws Exception {
        when(participacaoService.create(anyLong(), anyLong())).thenReturn(new Participacao());

        mockMvc.perform(post("/api/participacoes?idAtleta=1&idCorrida=2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(participacaoService).create(1L, 2L);
    }

    @Test
    @DisplayName("DELETE /api/participacoes cancela inscrição e retorna 204")
    void deveDeletarParticipacao() throws Exception {
        doNothing().when(participacaoService).delete(1L, 2L);

        mockMvc.perform(delete("/api/participacoes?idAtleta=1&idCorrida=2"))
                .andExpect(status().isNoContent());

        verify(participacaoService).delete(1L, 2L);
    }

    @Test
    @DisplayName("POST /api/participacoes deve retornar 400 se atleta já inscrito")
    void deveRetornar400QuandoJaInscrito() throws Exception {
        when(participacaoService.create(anyLong(), anyLong()))
                .thenThrow(new RuntimeException("Atleta já inscrito nesta corrida"));

        mockMvc.perform(post("/api/participacoes?idAtleta=1&idCorrida=2"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Atleta já inscrito nesta corrida"));
    }

    @Test
    @DisplayName("POST /api/participacoes deve retornar 400 se atleta já inscrito")
    void deveRetornar400QuandoAtletaJaInscrito() throws Exception {
        when(participacaoService.create(anyLong(), anyLong()))
                .thenThrow(new RuntimeException("Atleta já inscrito nesta corrida"));

        mockMvc.perform(post("/api/participacoes?idAtleta=1&idCorrida=2"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Atleta já inscrito nesta corrida"));
    }

}
