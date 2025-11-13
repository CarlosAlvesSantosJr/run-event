package com.example.runevent.controller;

import com.example.runevent.controller.AtletaController;
import com.example.runevent.model.Atleta;
import com.example.runevent.model.Login;
import com.example.runevent.service.AtletaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("removal")
@WebMvcTest(controllers = AtletaController.class)
class AtletaControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private AtletaService atletaService;
    @Autowired private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/atletas cria atleta e retorna 201")
    void deveCriarAtleta() throws Exception {
        Login login = new Login();
        login.setEmail("teste@email.com");
        login.setSenha("123456");

        Atleta atleta = new Atleta();
        atleta.setIdAtleta(1L);
        atleta.setNome("João");
        atleta.setLogin(login);
        atleta.setDataNascimento(LocalDate.of(1990,1,1));
        atleta.setGenero(Atleta.Genero.M);

        when(atletaService.create(any(Atleta.class))).thenReturn(atleta);

        String payload = """
            {
              "nome": "João",
              "dataNascimento": "1990-01-01",
              "genero": "M",
              "login": { "email": "teste@email.com", "senha": "123456" }
            }
            """;

        mockMvc.perform(post("/api/atletas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/atletas/1"))
                .andExpect(jsonPath("$.nome").value("João"));

        verify(atletaService).create(any(Atleta.class));
    }

    @Test
    @DisplayName("GET /api/atletas/{id} retorna 200")
    void deveBuscarAtletaPorId() throws Exception {
        Atleta atleta = new Atleta();
        atleta.setIdAtleta(10L);
        atleta.setNome("Teste X");
        when(atletaService.findById(10L)).thenReturn(atleta);

        mockMvc.perform(get("/api/atletas/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Teste X"));

        verify(atletaService).findById(10L);
    }

    @Test
    @DisplayName("DELETE /api/atletas/{id} retorna 204")
    void deveDeletarAtleta() throws Exception {
        doNothing().when(atletaService).delete(5L);

        mockMvc.perform(delete("/api/atletas/5"))
                .andExpect(status().isNoContent());

        verify(atletaService).delete(5L);
    }

    @Test
    @DisplayName("GET /api/atletas/{id} deve retornar 404 se não encontrado")
    void deveRetornar404QuandoAtletaNaoEncontrado() throws Exception {
        when(atletaService.findById(99L)).thenThrow(new RuntimeException("Atleta não encontrado"));

        mockMvc.perform(get("/api/atletas/99"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Atleta não encontrado"));
    }

    @Test
    @DisplayName("POST /api/atletas deve retornar 400 se email já existir")
    void deveRetornar400QuandoEmailDuplicado() throws Exception {
        when(atletaService.create(any(Atleta.class)))
                .thenThrow(new RuntimeException("E-mail já cadastrado"));

        String payload = """
        {
          "nome": "João",
          "dataNascimento": "1990-01-01",
          "genero": "M",
          "login": { "email": "teste@email.com", "senha": "123456" }
        }
        """;

        mockMvc.perform(post("/api/atletas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("E-mail já cadastrado"));
    }

    @Test
    @DisplayName("GET /api/atletas/{id} deve retornar 400 se id for inválido")
    void deveRetornar400QuandoIdInvalido() throws Exception {
        mockMvc.perform(get("/api/atletas/0")) // ID zero é inválido
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("ID inválido: deve ser maior que zero"));
    }

}
