package com.example.runevent.service;

import com.example.runevent.model.Atleta;
import com.example.runevent.model.Corrida;
import com.example.runevent.model.Participacao;
import com.example.runevent.model.ParticipacaoId;
import com.example.runevent.repository.ParticipacaoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParticipacaoService {

    private final ParticipacaoRepository participacaoRepository;
    private final AtletaService atletaService; // Dependência para buscar Atleta
    private final CorridaService corridaService; // Dependência para buscar Corrida

    public ParticipacaoService(ParticipacaoRepository participacaoRepository,
                               AtletaService atletaService,
                               CorridaService corridaService) {
        this.participacaoRepository = participacaoRepository;
        this.atletaService = atletaService;
        this.corridaService = corridaService;
    }

    /**
     * REGRA DE NEGÓCIO: Inscrever um Atleta em uma Corrida.
     * 1. Valida se o Atleta existe.
     * 2. Valida se a Corrida existe.
     * 3. Cria a Chave Composta (ParticipacaoId).
     * 4. Valida se o atleta JÁ NÃO ESTÁ inscrito nesta corrida.
     * 5. Salva a participação.
     */
    public Participacao create(Long idAtleta, Long idCorrida) {
        // 1. Valida Atleta
        Atleta atleta = atletaService.findById(idAtleta);
        // 2. Valida Corrida
        Corrida corrida = corridaService.findById(idCorrida);

        // 3. Cria a Chave
        ParticipacaoId id = new ParticipacaoId(idCorrida, idAtleta);

        // 4. Valida se já existe
        Optional<Participacao> inscricaoExistente = participacaoRepository.findById(id);
        if (inscricaoExistente.isPresent()) {
            throw new RuntimeException("Atleta já inscrito nesta corrida.");
        }

        // 5. Salva
        Participacao novaParticipacao = new Participacao();
        novaParticipacao.setId(id);
        novaParticipacao.setAtleta(atleta);
        novaParticipacao.setCorrida(corrida);

        return participacaoRepository.save(novaParticipacao);
    }

    /**
     * REGRA DE NEGÓCIO: Cancelar inscrição.
     */
    public void delete(Long idAtleta, Long idCorrida) {
        ParticipacaoId id = new ParticipacaoId(idCorrida, idAtleta);

        Participacao participacao = participacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Participação não encontrada."));

        participacaoRepository.delete(participacao);
    }
}
