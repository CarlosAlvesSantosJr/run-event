package com.example.runevent.service;

import com.example.runevent.model.Categoria;
import com.example.runevent.repository.CategoriaRepository;
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
class CategoriaServiceTest {

    @Mock private CategoriaRepository categoriaRepository;
    @InjectMocks private CategoriaService categoriaService;

    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = new Categoria(1L, "Adulto", "Categoria geral");
    }

    @Test
    @DisplayName("Deve criar categoria com sucesso")
    void deveCriarCategoria() {
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);
        Categoria salva = categoriaService.create(categoria);
        assertEquals("Adulto", salva.getNome());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar categoria inexistente")
    void deveLancarExcecaoAoBuscarInexistente() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> categoriaService.findById(1L));
    }

    @Test
    @DisplayName("Deve atualizar categoria existente")
    void deveAtualizarCategoria() {
        Categoria nova = new Categoria(1L, "Juvenil", "Nova descrição");
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.save(any())).thenReturn(nova);

        Categoria atualizada = categoriaService.update(1L, nova);
        assertEquals("Juvenil", atualizada.getNome());
    }

    @Test
    @DisplayName("Deve deletar categoria com sucesso")
    void deveDeletarCategoria() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        categoriaService.delete(1L);
        verify(categoriaRepository).delete(categoria);
    }
}
