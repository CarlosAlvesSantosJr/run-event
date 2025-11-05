package com.example.runevent.service;

import com.example.runevent.model.Categoria;
import com.example.runevent.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    /**
     * REGRA DE NEGÓCIO: Criar uma nova categoria.
     */
    public Categoria create(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    /**
     * REGRA DE NEGÓCIO: Buscar categoria por ID.
     * Lança exceção se não encontrar.
     */
    public Categoria findById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada. ID: " + id));
    }

    /**
     * REGRA DE NEGÓCIO: Listar todas as categorias.
     */
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    /**
     * REGRA DE NEGÓCIO: Atualizar uma categoria.
     */
    public Categoria update(Long id, Categoria categoriaAtualizada) {
        Categoria categoriaExistente = findById(id); // Reusa a validação de existência

        categoriaExistente.setNome(categoriaAtualizada.getNome());
        categoriaExistente.setDescricao(categoriaAtualizada.getDescricao());

        return categoriaRepository.save(categoriaExistente);
    }

    /**
     * REGRA DE NEGÓCIO: Deletar uma categoria.
     */
    public void delete(Long id) {
        Categoria categoria = findById(id); // Reusa a validação
        categoriaRepository.delete(categoria);
    }
}