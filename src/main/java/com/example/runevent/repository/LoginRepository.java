package com.example.runevent.repository;

import com.example.runevent.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {
    // Método essencial para verificar se um e-mail já existe
    Optional<Login> findByEmail(String email);
}
