package com.example.runevent.controller;

import com.example.runevent.controller.AdministradorController;
import com.example.runevent.model.Administrador;
import com.example.runevent.model.Login;
import com.example.runevent.service.AdministradorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("removal")
@WebMvcTest(controllers = AdministradorController.class)
class AdministradorControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private AdministradorService administradorService;
    @Autowired private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/administradores cria administrador e retorna 201")
    void deveCriarAdministrador() throws Exception {
        Login login = new Login();
        login.setEmail("adm@email.com");
        login.setSenha("senha123");

        Administrador adm = new Administrador();
        adm.setIdAdm(1L);
        adm.setNome("Administrador Teste");
        adm.setLogin(login);

        when(administradorService.create(any(Administrador.class))).thenReturn(adm);

        String payload = """
            {
              "nome": "Administrador Teste",
              "login": { "email": "adm@email.com", "senha": "senha123" }
            }
            """;

        mockMvc.perform(post("/api/administradores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/administradores/1"))
                .andExpect(jsonPath("$.nome").value("Administrador Teste"));

        verify(administradorService, times(1)).create(any(Administrador.class));
    }

    @Test
    @DisplayName("GET /api/administradores/{id} retorna 200 e administrador")
    void deveBuscarAdministradorPorId() throws Exception {
        Administrador adm = new Administrador();
        adm.setIdAdm(2L);
        adm.setNome("Admin 2");
        Login login = new Login();
        login.setEmail("a2@mail");
        adm.setLogin(login);

        when(administradorService.findById(2L)).thenReturn(adm);

        mockMvc.perform(get("/api/administradores/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Admin 2"));

        verify(administradorService).findById(2L);
    }

    @Test
    @DisplayName("DELETE /api/administradores/{id} retorna 204")
    void deveDeletarAdministrador() throws Exception {
        doNothing().when(administradorService).delete(3L);

        mockMvc.perform(delete("/api/administradores/3"))
                .andExpect(status().isNoContent());

        verify(administradorService).delete(3L);
    }
}
