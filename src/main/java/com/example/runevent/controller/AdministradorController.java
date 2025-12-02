package com.example.runevent.controller;

import com.example.runevent.dto.AdministradorCreateDTO;
import com.example.runevent.dto.LoginDTO;
import com.example.runevent.model.Administrador;
import com.example.runevent.model.Login;
import com.example.runevent.service.AdministradorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/administradores")
@Tag(name = "Administradores", description = "Endpoints para gerenciamento de administradores do sistema") // <--- TAG SWAGGER
public class AdministradorController {

    private final AdministradorService administradorService;

    public AdministradorController(AdministradorService administradorService) {
        this.administradorService = administradorService;
    }

    @GetMapping
    @Operation(
            summary = "Listar administradores",
            description = "Retorna uma lista com todos os administradores cadastrados",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista retornada com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Administrador.class))
                    )
            }
    )

    public ResponseEntity<List<Administrador>> findAll() {
        return ResponseEntity.ok(administradorService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar administrador por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Administrador encontrado",
                            content = @Content(schema = @Schema(implementation = Administrador.class))),
                    @ApiResponse(responseCode = "404", description = "Administrador não encontrado")
            }
    )
    public ResponseEntity<Administrador> findById(@PathVariable Long id) {
        return ResponseEntity.ok(administradorService.findById(id));
    }

    @PostMapping
    @Operation(
            summary = "Criar novo administrador",
            description = "Cria um administrador com nome e login associado",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Administrador criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação")
            }
    )
    public ResponseEntity<Administrador> create(
            @Valid @org.springframework.web.bind.annotation.RequestBody AdministradorCreateDTO dto) {

        Administrador adm = new Administrador();
        adm.setNome(dto.getNome());

        LoginDTO loginDTO = dto.getLogin();
        Login login = new Login();
        login.setEmail(loginDTO.getEmail());
        login.setSenha(loginDTO.getSenha());
        adm.setLogin(login);

        Administrador criado = administradorService.create(adm);
        return ResponseEntity.created(URI.create("/api/administradores/" + criado.getIdAdm())).body(criado);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar administrador",
            description = "Substitui completamente os dados de um administrador existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Administrador atualizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Administrador não encontrado")
            }
    )

    public ResponseEntity<Administrador> update(
            @PathVariable Long id,
            @Valid @org.springframework.web.bind.annotation.RequestBody AdministradorCreateDTO dto) {

        Administrador atualizado = administradorService.update(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir administrador",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Administrador removido com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Administrador não encontrado")
            }
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        administradorService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
