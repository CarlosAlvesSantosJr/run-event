package com.example.runevent.controller;

import com.example.runevent.model.Resultado;
import com.example.runevent.service.ResultadoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/resultados")
public class ResultadoController {

    private final ResultadoService resultadoService;

    public ResultadoController(ResultadoService resultadoService) {
        this.resultadoService = resultadoService;
    }

    @PostMapping
    public ResponseEntity<Resultado> create(@Valid @RequestBody Resultado resultado,
                                            @RequestParam Long idAtleta,
                                            @RequestParam Long idCorrida) {
        Resultado salvo = resultadoService.create(resultado, idAtleta, idCorrida);
        return ResponseEntity.created(URI.create("/api/resultados/" + salvo.getIdResultado())).body(salvo);
    }

    @GetMapping("/corrida/{idCorrida}")
    public ResponseEntity<List<Resultado>> findByCorrida(@PathVariable Long idCorrida) {
        return ResponseEntity.ok(resultadoService.findByCorrida(idCorrida));
    }
}
