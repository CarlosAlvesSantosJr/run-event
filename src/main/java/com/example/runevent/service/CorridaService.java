package com.example.runevent.service;

import com.example.runevent.dto.CorridaCreateDTO;
import com.example.runevent.model.Administrador;
import com.example.runevent.model.Corrida;
import com.example.runevent.repository.CorridaRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public Corrida update(Long id, CorridaCreateDTO dto, Long idAdm) {
        // 1. Busca e verifica a existência da corrida
        Corrida corridaExistente = corridaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Corrida com ID " + id + " não encontrada."));

        // 2. Aplica as modificações do DTO (todos os campos)
        corridaExistente.setNome(dto.getNome());
        corridaExistente.setData(dto.getData());
        corridaExistente.setLocal(dto.getLocal());
        corridaExistente.setDistancia(dto.getDistancia());

        // (Opcional) Implemente a verificação do idAdm se necessário:
        // Ex: Se o idAdm não for válido, lançar uma exceção.
        // adminService.findById(idAdm).orElseThrow(...);

        // 3. Salva a entidade atualizada
        return corridaRepository.save(corridaExistente);
    }

}
