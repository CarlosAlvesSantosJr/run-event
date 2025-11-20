package com.example.runevent.service;

import com.example.runevent.model.Administrador;
import com.example.runevent.model.Login;
import com.example.runevent.repository.AdministradorRepository;
import com.example.runevent.repository.LoginRepository;
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

    public Administrador update(Long id, Administrador adminAtualizado) {
        // 1. Buscar administrador existente
        Administrador existente = findById(id);

        // 2. Atualizar nome
        existente.setNome(adminAtualizado.getNome());

        // 3. Atualizar Login
        Login loginExistente = existente.getLogin();
        Login loginNovo = adminAtualizado.getLogin();

        if (loginNovo != null) {

            // Validar se e-mail foi alterado
            if (!loginExistente.getEmail().equals(loginNovo.getEmail())) {
                loginService.validateEmailUniqueness(loginNovo.getEmail());
            }

            loginExistente.setEmail(loginNovo.getEmail());
            loginExistente.setSenha(loginNovo.getSenha());
            loginExistente.setTipo(Login.TipoUsuario.ADM); // mantém o tipo ADM

            loginRepository.save(loginExistente);
        }

        // 4. Salvar administrador atualizado
        return administradorRepository.save(existente);
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
