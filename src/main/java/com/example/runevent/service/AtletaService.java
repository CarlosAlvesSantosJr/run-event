package com.example.runevent.service;

import com.example.runevent.model.Atleta;
import com.example.runevent.model.Categoria;
import com.example.runevent.model.Login;
import com.example.runevent.repository.AtletaRepository;
import com.example.runevent.repository.CategoriaRepository;
import com.example.runevent.repository.LoginRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AtletaService {

    private final AtletaRepository atletaRepository;
    private final LoginRepository loginRepository;
    private final CategoriaRepository categoriaRepository;
    private final LoginService loginService; // Usando o serviço de validação

    public AtletaService(AtletaRepository atletaRepository,
                         LoginRepository loginRepository,
                         CategoriaRepository categoriaRepository,
                         LoginService loginService) {
        this.atletaRepository = atletaRepository;
        this.loginRepository = loginRepository;
        this.categoriaRepository = categoriaRepository;
        this.loginService = loginService;
    }

    /**
     * REGRA DE NEGÓCIO: Busca um atleta pelo ID.
     * Se não encontrar, lança uma exceção.
     */
    public Atleta findById(Long id) {
        return atletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atleta não encontrado. ID: " + id));
    }

    /**
     * REGRA DE NEGÓCIO: Lista todos os atletas.
     */
    public List<Atleta> findAll() {
        return atletaRepository.findAll();
    }

    /**
     * REGRA DE NEGÓCIO: Criar um novo atleta.
     * 1. Valida se o e-mail (login) já não existe.
     * 2. Valida se a categoria informada existe.
     * 3. Salva o Login.
     * 4. Salva o Atleta associado ao Login.
     */
    @Transactional
    public Atleta create(Atleta atleta) {
        // 1. Validar e-mail
        loginService.validateEmailUniqueness(atleta.getLogin().getEmail());

        // 2. Validar Categoria
        if (atleta.getCategoria() != null && atleta.getCategoria().getIdCategoria() != null) {
            Long idCategoria = atleta.getCategoria().getIdCategoria();
            Categoria categoria = categoriaRepository.findById(idCategoria)
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada. ID: " + idCategoria));
            atleta.setCategoria(categoria);
        }

        // 3. Configurar e salvar o Login
        Login novoLogin = atleta.getLogin();
        novoLogin.setTipo(Login.TipoUsuario.ATLETA);
        // (Lembrete: criptografar a senha na Entrega de Segurança JWT)
        loginRepository.save(novoLogin);

        // 4. Salvar o Atleta
        atleta.setLogin(novoLogin);
        return atletaRepository.save(atleta);
    }

    /**
     * REGRA DE NEGÓCIO: Deletar um atleta.
     */
    @Transactional
    public void delete(Long id) {
        Atleta atleta = findById(id); // Reusa a validação de existência

        atletaRepository.delete(atleta);
        loginRepository.delete(atleta.getLogin());
    }
}
