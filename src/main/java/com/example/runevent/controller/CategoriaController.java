package com.example.runevent.controller;

import com.example.runevent.dto.CategoriaDTO;
import com.example.runevent.model.Categoria;
import com.example.runevent.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
            summary = "Listar administradores",
            description = "Retorna todos os administradores cadastrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
            }
    )
    public ResponseEntity<List<Categoria>> findAll() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar administrador por ID",
            description = "Retorna os dados de um administrador específico",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Administrador encontrado"),
                    @ApiResponse(responseCode = "404", description = "Administrador não encontrado")
            }
    )
    public ResponseEntity<?> findById(@PathVariable Long id) {
        if (id <= 0) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "ID inválido: deve ser maior que zero"));
        }
        return ResponseEntity.ok(categoriaService.findById(id));
    }

    @PostMapping
    @Operation(
            summary = "Criar administrador",
            description = "Cadastra um novo administrador no sistema",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Administrador criado com sucesso")
            }
    )
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
    @Operation(
            summary = "Excluir administrador",
            description = "Remove um administrador do sistema",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Administrador removido com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Administrador não encontrado")
            }
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
