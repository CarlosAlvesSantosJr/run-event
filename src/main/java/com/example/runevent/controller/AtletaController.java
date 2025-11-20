package com.example.runevent.controller;

import com.example.runevent.dto.AtletaCreateDTO;
import com.example.runevent.dto.LoginDTO;
import com.example.runevent.model.Atleta;
import com.example.runevent.model.Categoria;
import com.example.runevent.model.Login;
import com.example.runevent.service.AtletaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
            description = "Retorna todos os atletas cadastrados."
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<List<Atleta>> findAll() {
        return ResponseEntity.ok(atletaService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar atleta por ID",
            description = "Retorna os dados de um atleta específico."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atleta encontrado"),
            @ApiResponse(responseCode = "404", description = "Atleta não encontrado")
    })
    public ResponseEntity<?> findById(@PathVariable Long id) {
        if (id <= 0) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "ID inválido: deve ser maior que zero"));
        }

        return ResponseEntity.ok(atletaService.findById(id));
    }

    @PostMapping
    @Operation(
            summary = "Criar atleta",
            description = "Cadastra um novo atleta no sistema."
    )
    @ApiResponse(responseCode = "201", description = "Atleta criado com sucesso")
    public ResponseEntity<Atleta> create(@Valid @RequestBody AtletaCreateDTO dto) {
        Atleta atleta = new Atleta();
        atleta.setNome(dto.getNome());
        atleta.setDataNascimento(dto.getDataNascimento());
        atleta.setGenero(dto.getGenero());

        Login login = new Login();
        login.setEmail(dto.getLogin().getEmail());
        login.setSenha(dto.getLogin().getSenha());
        atleta.setLogin(login);

        if (dto.getIdCategoria() != null) {
            Categoria c = new Categoria();
            c.setIdCategoria(dto.getIdCategoria());
            atleta.setCategoria(c);
        }

        Atleta criado = atletaService.create(atleta);
        return ResponseEntity.created(URI.create("/api/atletas/" + criado.getIdAtleta())).body(criado);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar atleta",
            description = "Atualiza os dados de um atleta existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atleta atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Atleta não encontrado")
    })
    public ResponseEntity<Atleta> update(
            @PathVariable Long id,
            @Valid @RequestBody Atleta dto) {

        return ResponseEntity.ok(atletaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir atleta",
            description = "Remove um atleta do sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Atleta removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Atleta não encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        atletaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
