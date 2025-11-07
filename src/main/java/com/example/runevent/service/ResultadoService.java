package com.example.runevent.service;

import com.example.runevent.model.Atleta;
import com.example.runevent.model.Corrida;
import com.example.runevent.model.Resultado;
import com.example.runevent.repository.ResultadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultadoService {

    private final ResultadoRepository resultadoRepository;
    private final AtletaService atletaService;
    private final CorridaService corridaService;

    public ResultadoService(ResultadoRepository resultadoRepository,
                            AtletaService atletaService,
                            CorridaService corridaService) {
        this.resultadoRepository = resultadoRepository;
        this.atletaService = atletaService;
        this.corridaService = corridaService;
    }

    /**
     * REGRA DE NEGÓDICO: Lançar um resultado para um atleta em uma corrida.
     * 1. Valida se o Atleta existe.
     * 2. Valida se a Corrida existe.
     * 3. Associa Atleta e Corrida ao resultado e salva.
     */
    public Resultado create(Resultado resultado, Long idAtleta, Long idCorrida) {
        // 1. Valida Atleta
        Atleta atleta = atletaService.findById(idAtleta);
        // 2. Valida Corrida
        Corrida corrida = corridaService.findById(idCorrida);

        // 3. Associa e salva
        resultado.setAtleta(atleta);
        resultado.setCorrida(corrida);

        return resultadoRepository.save(resultado);
    }

    public Resultado findById(Long idResultado) {
        return resultadoRepository.findById(idResultado)
                .orElseThrow(() -> new RuntimeException("Resultado não encontrado. ID: " + idResultado));
    }

    /**
     * REGRA DE NEGÓCIO: Buscar todos os resultados de uma corrida específica.
     */
    public List<Resultado> findByCorrida(Long idCorrida) {
        // Valida se a corrida existe
        corridaService.findById(idCorrida);

        // O ideal é criar um método no Repository:
        // return resultadoRepository.findByCorridaIdCorridaOrderByColocacaoGeralAsc(idCorrida);

        // Simulação (menos eficiente):
        return resultadoRepository.findAll().stream()
                .filter(r -> r.getCorrida().getIdCorrida().equals(idCorrida))
                .toList();
    }
}
