package com.example.runevent.service;

import com.example.runevent.dto.AdministradorCreateDTO;
import com.example.runevent.model.Administrador;
import com.example.runevent.model.Login;
import com.example.runevent.repository.AdministradorRepository;
import com.example.runevent.repository.LoginRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministradorService {

    private final AdministradorRepository administradorRepository;
    private final LoginRepository loginRepository;
    private final LoginService loginService; // Reutilizando o serviço de validação

    public AdministradorService(AdministradorRepository administradorRepository,
                                LoginRepository loginRepository,
                                LoginService loginService) {
        this.administradorRepository = administradorRepository;
        this.loginRepository = loginRepository;
        this.loginService = loginService;
    }

    public Administrador findById(Long id) {
        return administradorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Administrador não encontrado. ID: " + id));
    }

    public List<Administrador> findAll() {
        return administradorRepository.findAll();
    }

    /**
     * REGRA DE NEGÓCIO: Criar um novo Administrador.
     * 1. Valida se o e-mail (login) já não existe.
     * 2. Salva o Login.
     * 3. Salva o Administrador associado ao Login.
     */

    public Administrador create(Administrador administrador) {
        // 1. Validar e-mail
        loginService.validateEmailUniqueness(administrador.getLogin().getEmail());

        // 2. Configurar e salvar o Login
        Login novoLogin = administrador.getLogin();
        novoLogin.setTipo(Login.TipoUsuario.ADM);
        loginRepository.save(novoLogin);

        // 3. Salvar o Administrador
        administrador.setLogin(novoLogin);
        return administradorRepository.save(administrador);
    }

    /**
     * REGRA DE NEGÓCIO: Atualizar um Administrador.
     */

    public Administrador update(Long id, AdministradorCreateDTO dto) {
        // 1. Busca e verifica a existência do administrador.
        // Lança EntityNotFoundException se o ID não for encontrado.
        Administrador admExistente = administradorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Administrador com ID " + id + " não encontrado."));

        // 2. Aplica as modificações do DTO.
        admExistente.setNome(dto.getNome());

        // Atualiza Login
        Login loginExistente = admExistente.getLogin();

        // Assumindo que o objeto Login já existe e tem seu próprio ID
        // e está relacionado ao Administrador.

        // Atualiza Email
        loginExistente.setEmail(dto.getLogin().getEmail());

        // Atualiza Senha: ⚠️ ATENÇÃO: Se o seu projeto usa encriptação (hashing) de senha,
        // o processamento (ex: chamar BCryptPasswordEncoder.encode()) deve ser feito aqui
        // antes de setar a senha.
        loginExistente.setSenha(dto.getLogin().getSenha());

        // 3. Salva a entidade atualizada (o loginExistente também será atualizado via cascata/relacionamento)
        return administradorRepository.save(admExistente);
    }

    /**
     * REGRA DE NEGÓCIO: Deletar um Administrador.
     */

    public void delete(Long id) {
        Administrador adm = findById(id);

        administradorRepository.delete(adm);
        loginRepository.delete(adm.getLogin()); // Remove o login associado
    }
}
