package com.example.runevent.controller;

import com.example.runevent.dto.AtletaCreateDTO;
import com.example.runevent.dto.LoginDTO;
import com.example.runevent.model.Atleta;
import com.example.runevent.model.Categoria;
import com.example.runevent.model.Login;
import com.example.runevent.service.AtletaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/atletas")
public class AtletaController {

    private final AtletaService atletaService;

    public AtletaController(AtletaService atletaService) {
        this.atletaService = atletaService;
    }

    @GetMapping
    @Operation(
            summary = "Listar atletas",
            description = "Retorna todos os atletas cadastrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
            }
    )
    public ResponseEntity<List<Atleta>> findAll() {
        return ResponseEntity.ok(atletaService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar atleta por ID",
            description = "Retorna os dados de um atleta específico",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Atleta encontrado"),
                    @ApiResponse(responseCode = "404", description = "Atleta não encontrado")
            }
    )
    public ResponseEntity<?> findById(@PathVariable Long id) {
        // 👇 ADICIONADO: checagem simples que cria outro "branch"
        if (id <= 0) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "ID inválido: deve ser maior que zero"));
        }

        return ResponseEntity.ok(atletaService.findById(id));
    }

    @PostMapping
    @Operation(
            summary = "Criar atleta",
            description = "Cadastra um novo atleta no sistema",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Atleta criado com sucesso")
            }
    )
    public ResponseEntity<Atleta> create(@Valid @RequestBody AtletaCreateDTO dto) {
        Atleta atleta = new Atleta();
        atleta.setNome(dto.getNome());
        atleta.setDataNascimento(dto.getDataNascimento());
        atleta.setGenero(dto.getGenero());

        LoginDTO l = dto.getLogin();
        Login login = new Login();
        login.setEmail(l.getEmail());
        login.setSenha(l.getSenha());
        atleta.setLogin(login);

        if (dto.getIdCategoria() != null) {
            Categoria cat = new Categoria();
            cat.setIdCategoria(dto.getIdCategoria());
            atleta.setCategoria(cat);
        }

        Atleta criado = atletaService.create(atleta);
        return ResponseEntity.created(URI.create("/api/atletas/" + criado.getIdAtleta())).body(criado);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar atleta",
            description = "Substitui completamente os dados de um atleta existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Atleta atualizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Atleta não encontrado")
            }
    )
    public ResponseEntity<Atleta> update(@PathVariable Long id, @Valid @RequestBody AtletaCreateDTO dto) {
        Atleta atletaAtualizado = atletaService.update(id, dto); // Você precisará adaptar o service
        return ResponseEntity.ok(atletaAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir atleta",
            description = "Remove um atleta do sistema",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Atleta removido com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Atleta não encontrado")
            }
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        atletaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
