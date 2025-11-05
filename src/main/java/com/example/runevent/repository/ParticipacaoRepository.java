package com.example.runevent.repository;

import com.example.runevent.model.Participacao;
import com.example.runevent.model.ParticipacaoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Note que o ID é a classe 'ParticipacaoId', não 'Long'
@Repository
public interface ParticipacaoRepository extends JpaRepository<Participacao, ParticipacaoId> {
}
