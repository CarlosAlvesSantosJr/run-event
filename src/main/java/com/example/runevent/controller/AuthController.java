package com.example.runevent.controller;

import com.example.runevent.dto.AuthRequestDto;
import com.example.runevent.dto.AuthResponseDto;
import com.example.runevent.security.JwtUtil; // ajuste o package se o seu JwtUtil estiver em outro pacote
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(
            summary = "Autenticação do usuário",
            description = "Valida e-mail e senha, retornando um token JWT para acesso ao sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login efetuado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
            @ApiResponse(responseCode = "500", description = "Erro ao autenticar")
    })
    public ResponseEntity<?> login(@RequestBody AuthRequestDto loginRequest) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getSenha()
                    )
            );

            UserDetails user = (UserDetails) auth.getPrincipal();
            String token = jwtUtil.generateToken(user);

            return ResponseEntity.ok(new AuthResponseDto(token));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Login ou senha inválidos");
        }
    }
}
