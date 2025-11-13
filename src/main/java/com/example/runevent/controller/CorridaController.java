package com.example.runevent.controller;

import com.example.runevent.dto.CorridaCreateDTO;
import com.example.runevent.model.Corrida;
import com.example.runevent.service.CorridaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/corridas")
public class CorridaController {

    private final CorridaService corridaService;

    public CorridaController(CorridaService corridaService) {
        this.corridaService = corridaService;
    }

    @GetMapping
    public ResponseEntity<List<Corrida>> findAll() {
        return ResponseEntity.ok(corridaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Corrida> findById(@PathVariable Long id) {
        return ResponseEntity.ok(corridaService.findById(id));
    }

    // Exige idAdm como request param
    @PostMapping
    public ResponseEntity<Corrida> create(@Valid @RequestBody CorridaCreateDTO dto,
                                          @RequestParam Long idAdm) {
        Corrida corrida = new Corrida();
        corrida.setNome(dto.getNome());
        corrida.setData(dto.getData());
        corrida.setLocal(dto.getLocal());
        corrida.setDistancia(dto.getDistancia());
        Corrida criado = corridaService.create(corrida, idAdm);
        return ResponseEntity.created(URI.create("/api/corridas/" + criado.getIdCorrida())).body(criado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        corridaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
