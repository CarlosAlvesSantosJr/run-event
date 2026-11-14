package com.example.runevent.controller;

import com.example.runevent.model.Categoria;
import com.example.runevent.service.CategoriaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("removal")
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = CategoriaController.class)
class CategoriaControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private CategoriaService categoriaService;
    @Autowired private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/categorias cria categoria e retorna 201")
    void deveCriarCategoria() throws Exception {
        Categoria categoria = new Categoria(1L, "Adulto", "Categoria principal");
        when(categoriaService.create(any(Categoria.class))).thenReturn(categoria);

        String payload = """
            {
              "nome": "Adulto",
              "descricao": "Categoria principal"
            }
            """;

        mockMvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/categorias/1"))
                .andExpect(jsonPath("$.nome").value("Adulto"));
    }

    @Test
    @DisplayName("GET /api/categorias retorna lista 200")
    void deveListarCategorias() throws Exception {
        when(categoriaService.findAll()).thenReturn(List.of(new Categoria(1L, "Infantil", "Menores de 15")));

        mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Infantil"));
    }

    @Test
    @DisplayName("DELETE /api/categorias/{id} retorna 204")
    void deveDeletarCategoria() throws Exception {
        doNothing().when(categoriaService).delete(1L);

        mockMvc.perform(delete("/api/categorias/1"))
                .andExpect(status().isNoContent());

        verify(categoriaService).delete(1L);
    }

    @Test
    @DisplayName("DELETE /api/categorias/{id} deve retornar 400 se categoria não existir")
    void deveRetornar400QuandoCategoriaNaoExistir() throws Exception {
        doThrow(new RuntimeException("Categoria não encontrada")).when(categoriaService).delete(99L);

        mockMvc.perform(delete("/api/categorias/99"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Categoria não encontrada"));
    }

    @Test
    @DisplayName("GET /api/categorias/{id} deve retornar 400 se não encontrada")
    void deveRetornar400QuandoCategoriaNaoEncontrada() throws Exception {
        when(categoriaService.findById(99L))
                .thenThrow(new RuntimeException("Categoria não encontrada"));

        mockMvc.perform(get("/api/categorias/99"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Categoria não encontrada"));
    }

    @Test
    @DisplayName("GET /api/categorias/{id} deve retornar 400 se id for inválido")
    void deveRetornar400QuandoIdInvalido() throws Exception {
        mockMvc.perform(get("/api/categorias/0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("ID inválido: deve ser maior que zero"));
    }

}
