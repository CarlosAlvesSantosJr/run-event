package com.example.runevent.controller;

import com.example.runevent.dto.AdministradorCreateDTO;
import com.example.runevent.dto.LoginDTO;
import com.example.runevent.model.Administrador;
import com.example.runevent.model.Login;
import com.example.runevent.service.AdministradorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/administradores")
public class AdministradorController {

    private final AdministradorService administradorService;

    public AdministradorController(AdministradorService administradorService) {
        this.administradorService = administradorService;
    }

    @GetMapping
    @Operation(
            summary = "Listar administradores",
            description = "Retorna todos os administradores cadastrados."
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<List<Administrador>> findAll() {
        return ResponseEntity.ok(administradorService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar administrador por ID",
            description = "Retorna os dados de um administrador específico."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Administrador encontrado"),
            @ApiResponse(responseCode = "404", description = "Administrador não encontrado")
    })
    public ResponseEntity<Administrador> findById(@PathVariable Long id) {
        return ResponseEntity.ok(administradorService.findById(id));
    }

    @PostMapping
    @Operation(
            summary = "Criar administrador",
            description = "Cadastra um novo administrador no sistema."
    )
    @ApiResponse(responseCode = "201", description = "Administrador criado com sucesso")
    public ResponseEntity<Administrador> create(@Valid @RequestBody AdministradorCreateDTO dto) {
        Administrador adm = new Administrador();
        adm.setNome(dto.getNome());

        Login login = new Login();
        login.setEmail(dto.getLogin().getEmail());
        login.setSenha(dto.getLogin().getSenha());
        adm.setLogin(login);

        Administrador criado = administradorService.create(adm);
        return ResponseEntity.created(URI.create("/api/administradores/" + criado.getIdAdm())).body(criado);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar administrador",
            description = "Atualiza os dados de um administrador existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Administrador atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Administrador não encontrado")
    })
    public ResponseEntity<Administrador> update(
            @PathVariable Long id,
            @Valid @RequestBody AdministradorCreateDTO dto) {

        Administrador adm = new Administrador();
        adm.setNome(dto.getNome());

        Login login = new Login();
        login.setEmail(dto.getLogin().getEmail());
        login.setSenha(dto.getLogin().getSenha());
        adm.setLogin(login);

        Administrador atualizado = administradorService.update(id, adm);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir administrador",
            description = "Remove um administrador do sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Administrador removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Administrador não encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        administradorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
