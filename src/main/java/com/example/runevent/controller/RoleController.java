package com.example.runevent.controller;


import com.example.runevent.dto.RoleDto;
import com.example.runevent.model.Role;
import com.example.runevent.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    @Operation(
            summary = "Criar role",
            description = "Cadastra uma nova role no sistema."
    )
    @ApiResponse(responseCode = "201", description = "Role criada com sucesso")
    public RoleDto criar(@RequestBody Role role) {
        return roleService.criar(role);
    }

    @GetMapping
    @Operation(
            summary = "Listar roles",
            description = "Retorna todas as roles cadastradas."
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public List<RoleDto> listarTodas() {
        return roleService.listarTodas();
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar role",
            description = "Atualiza os dados de uma role existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Role não encontrada")
    })
    public ResponseEntity<RoleDto> atualizar(@PathVariable Long id, @RequestBody Role role) {
        RoleDto roleAtualizada = roleService.atualizar(id, role);
        return ResponseEntity.ok(roleAtualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir role",
            description = "Remove uma role do sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Role removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Role não encontrada")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        roleService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar role por ID",
            description = "Retorna os dados de uma role específica."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role encontrada"),
            @ApiResponse(responseCode = "404", description = "Role não encontrada")
    })
    public ResponseEntity<RoleDto> buscarPorId(@PathVariable Long id) {
        RoleDto role = roleService.buscarPorId(id);
        return ResponseEntity.ok(role);
    }


    @GetMapping("/buscar")
    @Operation(
            summary = "Buscar role por descrição",
            description = "Retorna uma role através da sua descrição."
    )
    @ApiResponse(responseCode = "200", description = "Role encontrada")
    public ResponseEntity<RoleDto> buscarPorNome(@RequestParam String descricao) {
        RoleDto role = roleService.buscarPordescricao(descricao);
        return ResponseEntity.ok(role);
    }
}
