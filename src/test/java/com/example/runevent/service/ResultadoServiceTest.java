package com.example.runevent.service;

import com.example.runevent.model.Atleta;
import com.example.runevent.model.Corrida;
import com.example.runevent.model.Resultado;
import com.example.runevent.repository.ResultadoRepository;
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
class ResultadoServiceTest {

    @Mock private ResultadoRepository resultadoRepository;
    @Mock private AtletaService atletaService;
    @Mock private CorridaService corridaService;

    @InjectMocks private ResultadoService resultadoService;

    private Atleta atleta;
    private Corrida corrida;
    private Resultado resultado;

    @BeforeEach
    void setUp() {
        atleta = new Atleta();
        atleta.setIdAtleta(1L);
        corrida = new Corrida();
        corrida.setIdCorrida(1L);
        resultado = new Resultado(1L, corrida, atleta, null, 1, 1);
    }

    @Test
    @DisplayName("Deve criar resultado com sucesso")
    void deveCriarResultado() {
        when(atletaService.findById(1L)).thenReturn(atleta);
        when(corridaService.findById(1L)).thenReturn(corrida);
        when(resultadoRepository.save(any(Resultado.class))).thenReturn(resultado);

        Resultado salvo = resultadoService.create(resultado, 1L, 1L);
        assertNotNull(salvo);
        verify(resultadoRepository).save(any(Resultado.class));
    }

    @Test
    @DisplayName("Deve lançar exceção se resultado não encontrado")
    void deveLancarExcecaoAoBuscarInexistente() {
        when(resultadoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> resultadoService.findById(1L));
    }

    @Test
    @DisplayName("Deve retornar resultados de uma corrida")
    void deveRetornarResultadosPorCorrida() {
        when(resultadoRepository.findAll()).thenReturn(List.of(resultado));
        List<Resultado> lista = resultadoService.findByCorrida(1L);
        assertEquals(1, lista.size());
    }
}
