package com.example.runevent.service;

import com.example.runevent.model.Login;
import com.example.runevent.repository.LoginRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock private LoginRepository loginRepository;
    @InjectMocks private LoginService loginService;

    @Test
    @DisplayName("Deve lançar exceção se e-mail já existir")
    void deveLancarExcecaoQuandoEmailExistir() {
        when(loginRepository.findByEmail("teste@email.com"))
                .thenReturn(Optional.of(new Login()));
        assertThrows(RuntimeException.class,
                () -> loginService.validateEmailUniqueness("teste@email.com"));
    }

    @Test
    @DisplayName("Não deve lançar exceção se e-mail for novo")
    void naoDeveLancarExcecaoParaEmailNovo() {
        when(loginRepository.findByEmail("novo@email.com"))
                .thenReturn(Optional.empty());
        assertDoesNotThrow(() -> loginService.validateEmailUniqueness("novo@email.com"));
    }

    @Test
    @DisplayName("Deve lançar exceção ao validar e-mail duplicado")
    void deveLancarExcecaoQuandoEmailDuplicado() {
        when(loginRepository.findByEmail("teste@email.com"))
                .thenReturn(Optional.of(new Login()));

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                loginService.validateEmailUniqueness("teste@email.com"));

        assertTrue(ex.getMessage().contains("E-mail já cadastrado"));
    }
}
