package com.example.runevent.repository;

import com.example.runevent.model.Atleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtletaRepository extends JpaRepository<Atleta, Long> {

    Optional<Atleta> findByLogin_Email(String email);

    @Query("SELECT a FROM Atleta a WHERE LOWER(a.nome) LIKE CONCAT('%', LOWER(:nome), '%')")
    List<Atleta> searchByNome(@Param("nome") String nome); // para buscar pelo nome ja com tratamento de caracteres
}
