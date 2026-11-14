package com.example.runevent.service;

import com.example.runevent.model.Atleta;
import com.example.runevent.model.Categoria;
import com.example.runevent.model.Login;
import com.example.runevent.repository.AtletaRepository;
import com.example.runevent.repository.CategoriaRepository;
import com.example.runevent.repository.LoginRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtletaServiceTest {

    @Mock private AtletaRepository atletaRepository;
    @Mock private LoginRepository loginRepository;
    @Mock private CategoriaRepository categoriaRepository;
    @Mock private LoginService loginService;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks private AtletaService atletaService;

    private Atleta atleta;
    private Categoria categoria;
    private Login login;

    @BeforeEach
    void setUp() {
        login = new Login();
        login.setEmail("teste@email.com");
        login.setSenha("123456");

        categoria = new Categoria();
        categoria.setIdCategoria(1L);
        categoria.setNome("Adulto");

        atleta = new Atleta();
        atleta.setIdAtleta(1L);
        atleta.setNome("João");
        atleta.setLogin(login);
        atleta.setCategoria(categoria);
    }

    @Test
    @DisplayName("Deve criar atleta com sucesso")
    void deveCriarAtletaComSucesso() {
        doNothing().when(loginService).validateEmailUniqueness(anyString());
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(passwordEncoder.encode("123456")).thenReturn("senha-criptografada");
        when(atletaRepository.save(any())).thenReturn(atleta);

        Atleta salvo = atletaService.create(atleta);

        assertNotNull(salvo);
        assertEquals("João", salvo.getNome());
        assertEquals("senha-criptografada", salvo.getLogin().getSenha());
        verify(loginRepository, times(1)).save(any(Login.class));
        verify(atletaRepository, times(1)).save(any(Atleta.class));
    }

    @Test
    @DisplayName("Deve lançar exceção se categoria não existir")
    void deveLancarExcecaoQuandoCategoriaNaoEncontrada() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                atletaService.create(atleta));

        assertTrue(ex.getMessage().contains("Categoria não encontrada"));
    }

    @Test
    @DisplayName("Deve buscar atleta por ID com sucesso")
    void deveBuscarAtletaPorId() {
        when(atletaRepository.findById(1L)).thenReturn(Optional.of(atleta));

        Atleta encontrado = atletaService.findById(1L);

        assertNotNull(encontrado);
        assertEquals("João", encontrado.getNome());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar atleta inexistente")
    void deveLancarExcecaoQuandoAtletaNaoEncontrado() {
        when(atletaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> atletaService.findById(1L));
    }

    @Test
    @DisplayName("Deve criar atleta sem categoria vinculada")
    void deveCriarAtletaSemCategoria() {
        // Simula atleta sem categoria
        atleta.setCategoria(null);

        // Não lança exceção de e-mail duplicado
        doNothing().when(loginService).validateEmailUniqueness(anyString());
        when(atletaRepository.save(any())).thenReturn(atleta);

        // Executa o método
        Atleta salvo = atletaService.create(atleta);

        // Verificações
        assertNotNull(salvo);
        assertEquals("João", salvo.getNome());
        verify(categoriaRepository, never()).findById(any()); // não deve buscar categoria
        verify(atletaRepository, times(1)).save(any(Atleta.class));
    }
}
