package com.example.runevent.controller;

import com.example.runevent.dto.AuthRequestDto;
import com.example.runevent.dto.AuthResponseDto;
import com.example.runevent.security.JwtUtil; // ajuste o package se o seu JwtUtil estiver em outro pacote
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getSenha()
                    )
            );

            Object principal = authentication.getPrincipal();
            if (!(principal instanceof UserDetails)) {
                return ResponseEntity.status(500).body("Erro interno ao obter detalhes do usuário.");
            }
            UserDetails userDetails = (UserDetails) principal;

            String token = jwtUtil.generateToken(userDetails);

            AuthResponseDto response = new AuthResponseDto();
            response.setToken(token);

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Login ou senha inválidos");
        }
    }
}