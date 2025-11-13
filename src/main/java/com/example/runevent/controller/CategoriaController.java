package com.example.runevent.controller;

import com.example.runevent.dto.CategoriaDTO;
import com.example.runevent.model.Categoria;
import com.example.runevent.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> findAll() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        if (id <= 0) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "ID inválido: deve ser maior que zero"));
        }
        return ResponseEntity.ok(categoriaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Categoria> create(@Valid @RequestBody CategoriaDTO dto) {
        Categoria c = new Categoria();
        c.setNome(dto.getNome());
        c.setDescricao(dto.getDescricao());
        Categoria criado = categoriaService.create(c);
        return ResponseEntity.created(URI.create("/api/categorias/" + criado.getIdCategoria())).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> update(@PathVariable Long id, @Valid @RequestBody CategoriaDTO dto) {
        Categoria c = new Categoria();
        c.setNome(dto.getNome());
        c.setDescricao(dto.getDescricao());
        return ResponseEntity.ok(categoriaService.update(id, c));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
