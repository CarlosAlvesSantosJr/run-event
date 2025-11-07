package com.example.runevent.service;

import com.example.runevent.model.Administrador;
import com.example.runevent.model.Corrida;
import com.example.runevent.repository.CorridaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorridaService {

    private final CorridaRepository corridaRepository;
    private final AdministradorService administradorService;

    public CorridaService(CorridaRepository corridaRepository, AdministradorService administradorService) {
        this.corridaRepository = corridaRepository;
        this.administradorService = administradorService;
    }

    public Corrida findById(Long id) {
        return corridaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Corrida não encontrada. ID: " + id));
    }

    public List<Corrida> findAll() {
        return corridaRepository.findAll();
    }

    /**
     * REGRA DE NEGÓCIO: Criar uma nova Corrida.
     * 1. Valida se o Administrador (criador) existe.
     * 2. Associa o Administrador encontrado à Corrida.
     * 3. Salva a Corrida.
     */
    public Corrida create(Corrida corrida, Long idAdm) {
        // 1. Valida se o Administrador existe
        Administrador adm = administradorService.findById(idAdm);

        // 2. Associa o admin
        corrida.setAdministrador(adm);

        // 3. Salva a corrida
        return corridaRepository.save(corrida);
    }

    public void delete(Long id) {
        Corrida corrida = findById(id);
        corridaRepository.delete(corrida);
    }
}
