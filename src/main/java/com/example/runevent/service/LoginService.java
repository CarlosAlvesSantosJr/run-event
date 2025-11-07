package com.example.runevent.service;

import com.example.runevent.repository.LoginRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    /**
     * REGRA DE NEGÓCIO: Verifica se um e-mail já está em uso.
     * Lança uma exceção se o e-mail já existir.
     */
    public void validateEmailUniqueness(String email) {
        if (loginRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado: " + email);
        }
    }
}
