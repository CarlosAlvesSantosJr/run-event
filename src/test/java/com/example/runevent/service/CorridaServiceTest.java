package com.example.runevent.service;

import com.example.runevent.model.Administrador;
import com.example.runevent.model.Corrida;
import com.example.runevent.repository.CorridaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CorridaServiceTest {

    @Mock private CorridaRepository corridaRepository;
    @Mock private AdministradorService administradorService;
    @InjectMocks private CorridaService corridaService;

    private Corrida corrida;
    private Administrador adm;

    @BeforeEach
    void setUp() {
        adm = new Administrador();
        adm.setIdAdm(1L);
        corrida = new Corrida(1L, "Corrida da Serra", LocalDate.now(), "Florianópolis", 10.0, adm);
    }

    @Test
    @DisplayName("Deve criar corrida com sucesso")
    void deveCriarCorrida() {
        when(administradorService.findById(1L)).thenReturn(adm);
        when(corridaRepository.save(any())).thenReturn(corrida);

        Corrida salva = corridaService.create(corrida, 1L);

        assertNotNull(salva);
        assertEquals("Corrida da Serra", salva.getNome());
    }

    @Test
    @DisplayName("Deve lançar exceção se corrida não encontrada")
    void deveLancarExcecaoAoBuscarInexistente() {
        when(corridaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> corridaService.findById(1L));
    }
}
