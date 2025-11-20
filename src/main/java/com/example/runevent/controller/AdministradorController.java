package com.example.runevent.controller;

import com.example.runevent.dto.AdministradorCreateDTO;
import com.example.runevent.dto.LoginDTO;
import com.example.runevent.model.Administrador;
import com.example.runevent.model.Login;
import com.example.runevent.service.AdministradorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    public ResponseEntity<List<Administrador>> findAll() {
        return ResponseEntity.ok(administradorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administrador> findById(@PathVariable Long id) {
        return ResponseEntity.ok(administradorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Administrador> create(@Valid @RequestBody AdministradorCreateDTO dto) {
        Administrador adm = new Administrador();
        adm.setNome(dto.getNome());
        LoginDTO l = dto.getLogin();
        Login login = new Login();
        login.setEmail(l.getEmail());
        login.setSenha(l.getSenha());
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
    public ResponseEntity<Administrador> update(@PathVariable Long id,
                                                @Valid @RequestBody AdministradorCreateDTO dto) {
        Administrador administradorAtualizado = administradorService.update(id, dto);
        return ResponseEntity.ok(administradorAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        administradorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
