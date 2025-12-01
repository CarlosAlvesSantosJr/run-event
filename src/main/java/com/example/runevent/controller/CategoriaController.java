package com.example.runevent.controller;

import com.example.runevent.dto.CategoriaDTO;
import com.example.runevent.model.Categoria;
import com.example.runevent.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    @Operation(
            summary = "Listar categorias",
            description = "Retorna todas as categorias cadastradas."
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<List<Categoria>> findAll() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar categoria por ID",
            description = "Retorna os dados de uma categoria específica."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public ResponseEntity<?> findById(@PathVariable Long id) {
        if (id <= 0) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "ID inválido: deve ser maior que zero"));
        }
        return ResponseEntity.ok(categoriaService.findById(id));
    }

    @PostMapping
    @Operation(
            summary = "Criar categoria",
            description = "Cadastra uma nova categoria no sistema."
    )
    @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso")
    public ResponseEntity<Categoria> create(@Valid @RequestBody CategoriaDTO dto) {
        Categoria c = new Categoria();
        c.setNome(dto.getNome());
        c.setDescricao(dto.getDescricao());
        Categoria criado = categoriaService.create(c);
        return ResponseEntity.created(URI.create("/api/categorias/" + criado.getIdCategoria())).body(criado);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar categoria",
            description = "Atualiza os dados de uma categoria existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public ResponseEntity<Categoria> update(@PathVariable Long id, @Valid @RequestBody CategoriaDTO dto) {
        Categoria c = new Categoria();
        c.setNome(dto.getNome());
        c.setDescricao(dto.getDescricao());
        return ResponseEntity.ok(categoriaService.update(id, c));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir categoria",
            description = "Remove uma categoria do sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Categoria removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
