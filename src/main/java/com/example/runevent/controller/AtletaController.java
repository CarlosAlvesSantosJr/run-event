package com.example.runevent.controller;

import com.example.runevent.dto.AtletaCreateDTO;
import com.example.runevent.dto.LoginDTO;
import com.example.runevent.model.Atleta;
import com.example.runevent.model.Categoria;
import com.example.runevent.model.Login;
import com.example.runevent.service.AtletaService;
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
    public ResponseEntity<List<Atleta>> findAll() {
        return ResponseEntity.ok(atletaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        // 👇 ADICIONADO: checagem simples que cria outro "branch"
        if (id <= 0) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "ID inválido: deve ser maior que zero"));
        }

        return ResponseEntity.ok(atletaService.findById(id));
    }

    @PostMapping
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        atletaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
