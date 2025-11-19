package com.example.runevent.repository;

import com.example.runevent.model.Atleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AtletaRepository extends JpaRepository<Atleta, Long> {
    Optional<Atleta> findByLogin_Email(String email);
}
