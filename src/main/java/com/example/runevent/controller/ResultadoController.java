package com.example.runevent.controller;

import com.example.runevent.model.Resultado;
import com.example.runevent.service.ResultadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/resultados")
public class ResultadoController {

    private final ResultadoService resultadoService;

    public ResultadoController(ResultadoService resultadoService) {
        this.resultadoService = resultadoService;
    }

    @PostMapping
    @Operation(
            summary = "Registrar resultado",
            description = "Associa um resultado a um atleta em uma corrida."
    )
    @ApiResponse(responseCode = "201", description = "Resultado registrado com sucesso")
    public ResponseEntity<Resultado> create(
            @Valid @RequestBody Resultado resultado,
            @RequestParam Long idAtleta,
            @RequestParam Long idCorrida) {

        Resultado salvo = resultadoService.create(resultado, idAtleta, idCorrida);
        return ResponseEntity.created(URI.create("/api/resultados/" + salvo.getIdResultado())).body(salvo);
    }

    @GetMapping("/corrida/{idCorrida}")
    @Operation(
            summary = "Listar resultados por corrida",
            description = "Retorna todos os resultados associados a uma corrida específica."
    )
    @ApiResponse(responseCode = "200", description = "Resultados encontrados com sucesso")
    public ResponseEntity<List<Resultado>> findByCorrida(@PathVariable Long idCorrida) {
        return ResponseEntity.ok(resultadoService.findByCorrida(idCorrida));
    }
}
