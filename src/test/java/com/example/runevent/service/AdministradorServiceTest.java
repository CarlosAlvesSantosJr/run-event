package com.example.runevent.service;

import com.example.runevent.model.Administrador;
import com.example.runevent.model.Login;
import com.example.runevent.repository.AdministradorRepository;
import com.example.runevent.repository.LoginRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdministradorServiceTest {

    @Mock private AdministradorRepository administradorRepository;
    @Mock private LoginRepository loginRepository;
    @Mock private LoginService loginService;

    @InjectMocks private AdministradorService administradorService;

    private Administrador administrador;
    private Login login;

    @BeforeEach
    void setUp() {
        login = new Login();
        login.setEmail("adm@email.com");

        administrador = new Administrador();
        administrador.setIdAdm(1L);
        administrador.setNome("Administrador Teste");
        administrador.setLogin(login);
    }

    @Test
    @DisplayName("Deve criar administrador com sucesso")
    void deveCriarAdministrador() {
        doNothing().when(loginService).validateEmailUniqueness(anyString());
        when(loginRepository.save(any(Login.class))).thenReturn(login);
        when(administradorRepository.save(any(Administrador.class))).thenReturn(administrador);

        Administrador salvo = administradorService.create(administrador);

        assertNotNull(salvo);
        assertEquals("Administrador Teste", salvo.getNome());
        verify(loginService).validateEmailUniqueness("adm@email.com");
        verify(administradorRepository).save(any(Administrador.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar administrador inexistente")
    void deveLancarExcecaoAoBuscarInexistente() {
        when(administradorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> administradorService.findById(1L));
    }

    @Test
    @DisplayName("Deve deletar administrador com sucesso")
    void deveDeletarAdministrador() {
        when(administradorRepository.findById(1L)).thenReturn(Optional.of(administrador));
        administradorService.delete(1L);
        verify(administradorRepository).delete(administrador);
        verify(loginRepository).delete(login);
    }
}
