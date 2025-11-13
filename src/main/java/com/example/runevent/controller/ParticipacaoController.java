package com.example.runevent.controller;

import com.example.runevent.model.Participacao;
import com.example.runevent.service.ParticipacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/participacoes")
public class ParticipacaoController {

    private final ParticipacaoService participacaoService;

    public ParticipacaoController(ParticipacaoService participacaoService) {
        this.participacaoService = participacaoService;
    }

    @PostMapping
    public ResponseEntity<Participacao> create(@RequestParam Long idAtleta, @RequestParam Long idCorrida) {
        Participacao nova = participacaoService.create(idAtleta, idCorrida);
        return ResponseEntity.created(URI.create("/api/participacoes")).body(nova);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long idAtleta, @RequestParam Long idCorrida) {
        participacaoService.delete(idAtleta, idCorrida);
        return ResponseEntity.noContent().build();
    }
}
