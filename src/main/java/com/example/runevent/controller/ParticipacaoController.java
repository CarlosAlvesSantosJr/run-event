package com.example.runevent.controller;

import com.example.runevent.model.Participacao;
import com.example.runevent.service.ParticipacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/participacoes")
public class ParticipacaoController {

    private final ParticipacaoService participacaoService;

    public ParticipacaoController(ParticipacaoService participacaoService) {
        this.participacaoService = participacaoService;
    }

    @PostMapping
    @Operation(
            summary = "Registrar participação",
            description = "Inscreve um atleta em uma corrida."
    )
    @ApiResponse(responseCode = "201", description = "Participação registrada com sucesso")
    public ResponseEntity<Participacao> create(@RequestParam Long idAtleta, @RequestParam Long idCorrida) {
        Participacao nova = participacaoService.create(idAtleta, idCorrida);
        return ResponseEntity.created(URI.create("/api/participacoes")).body(nova);
    }

    @DeleteMapping
    @Operation(
            summary = "Excluir participação",
            description = "Remove a inscrição de um atleta em uma corrida."
    )
    @ApiResponse(responseCode = "204", description = "Participação removida com sucesso")
    public ResponseEntity<Void> delete(@RequestParam Long idAtleta, @RequestParam Long idCorrida) {
        participacaoService.delete(idAtleta, idCorrida);
        return ResponseEntity.noContent().build();
    }
}
