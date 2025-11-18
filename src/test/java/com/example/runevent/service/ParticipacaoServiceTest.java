package com.example.runevent.service;

import com.example.runevent.model.Atleta;
import com.example.runevent.model.Corrida;
import com.example.runevent.model.Participacao;
import com.example.runevent.model.ParticipacaoId;
import com.example.runevent.repository.ParticipacaoRepository;
import org.junit.jupiter.api.BeforeEach;
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
class ParticipacaoServiceTest {

    @Mock private ParticipacaoRepository participacaoRepository;
    @Mock private AtletaService atletaService;
    @Mock private CorridaService corridaService;
    @InjectMocks private ParticipacaoService participacaoService;

    private Atleta atleta;
    private Corrida corrida;

    @BeforeEach
    void setUp() {
        atleta = new Atleta();
        atleta.setIdAtleta(1L);
        corrida = new Corrida();
        corrida.setIdCorrida(1L);
    }

    @Test
    @DisplayName("Deve criar participação com sucesso")
    void deveCriarParticipacao() {
        when(atletaService.findById(1L)).thenReturn(atleta);
        when(corridaService.findById(1L)).thenReturn(corrida);
        when(participacaoRepository.findById(any())).thenReturn(Optional.empty());
        when(participacaoRepository.save(any())).thenReturn(new Participacao());

        Participacao p = participacaoService.create(1L, 1L);
        assertNotNull(p);
        verify(participacaoRepository).save(any(Participacao.class));
    }

    @Test
    @DisplayName("Deve lançar exceção se atleta já inscrito")
    void deveLancarExcecaoSeInscricaoExistir() {
        Participacao existente = new Participacao();
        when(participacaoRepository.findById(any())).thenReturn(Optional.of(existente));
        assertThrows(RuntimeException.class, () -> participacaoService.create(1L, 1L));
    }

    @Test
    @DisplayName("Deve deletar participação com sucesso")
    void deveDeletarParticipacao() {
        Participacao p = new Participacao();
        when(participacaoRepository.findById(any())).thenReturn(Optional.of(p));
        participacaoService.delete(1L, 1L);
        verify(participacaoRepository).delete(p);
    }
}
