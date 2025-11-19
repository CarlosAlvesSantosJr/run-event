package com.example.runevent.controller;

import com.example.runevent.dto.CorridaCreateDTO;
import com.example.runevent.model.Corrida;
import com.example.runevent.service.CorridaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(
            summary = "Listar corridas",
            description = "Retorna todas as corridas cadastradas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
            }
    )
    public ResponseEntity<List<Corrida>> findAll() {
        return ResponseEntity.ok(corridaService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar corrida por ID",
            description = "Retorna os dados de uma corrida específica",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Corrida encontrada"),
                    @ApiResponse(responseCode = "404", description = "Corrida não encontrada")
            }
    )
    public ResponseEntity<Corrida> findById(@PathVariable Long id) {
        return ResponseEntity.ok(corridaService.findById(id));
    }

    // Exige idAdm como request param
    @PostMapping
    @Operation(
            summary = "Criar corrida",
            description = "Registra uma nova corrida no sistema",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Corrida criada com sucesso")
            }
    )
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
    @Operation(
            summary = "Excluir corrida",
            description = "Remove uma corrida do sistema",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Corrida removida com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Corrida não encontrada")
            }
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        corridaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
