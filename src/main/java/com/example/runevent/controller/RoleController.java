package com.example.runevent.controller;


import com.example.runevent.dto.RoleDto;
import com.example.runevent.model.Role;
import com.example.runevent.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public RoleDto criar(@RequestBody Role role) {
        return roleService.criar(role);
    }

    @GetMapping
    public List<RoleDto> listarTodas() {
        return roleService.listarTodas();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> atualizar(@PathVariable Long id, @RequestBody Role role) {
        RoleDto roleAtualizada = roleService.atualizar(id, role);
        return ResponseEntity.ok(roleAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        roleService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> buscarPorId(@PathVariable Long id) {
        RoleDto role = roleService.buscarPorId(id);
        return ResponseEntity.ok(role);
    }


    @GetMapping("/buscar")
    public ResponseEntity<RoleDto> buscarPorNome(@RequestParam String descricao) {
        RoleDto role = roleService.buscarPordescricao(descricao);
        return ResponseEntity.ok(role);
    }
}
