package com.example.runevent.service;

import com.example.runevent.dto.AtletaCreateDTO;
import com.example.runevent.model.Atleta;
import com.example.runevent.model.Categoria;
import com.example.runevent.model.Login;
import com.example.runevent.repository.AtletaRepository;
import com.example.runevent.repository.CategoriaRepository;
import com.example.runevent.repository.LoginRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtletaService {

    private final AtletaRepository atletaRepository;
    private final LoginRepository loginRepository;
    private final CategoriaRepository categoriaRepository;
    private final LoginService loginService; // Usando o serviço de validação
    private final PasswordEncoder passwordEncoder;

    public AtletaService(AtletaRepository atletaRepository,
                         LoginRepository loginRepository,
                         CategoriaRepository categoriaRepository,
                         LoginService loginService,
                         PasswordEncoder passwordEncoder) {
        this.atletaRepository = atletaRepository;
        this.loginRepository = loginRepository;
        this.categoriaRepository = categoriaRepository;
        this.loginService = loginService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * REGRA DE NEGÓCIO: Busca atletas pelo nome (sem acentos, sem diferenciar maiúsculas/minúsculas)
     */
    public List<Atleta> findByNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome para pesquisa não pode ser vazio.");
        }

        // Normaliza o nome para tirar acentos e espaços desnecessários
        String nomeNormalizado = normalizeText(nome);

        return atletaRepository.searchByNome(nomeNormalizado);
    }

    /**
     * Normaliza texto removendo acentos, espaços extras e convertendo para lower-case
     */
    private String normalizeText(String texto) {
        String semAcentos = java.text.Normalizer.normalize(texto, java.text.Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]+", "");

        return semAcentos.toLowerCase().trim();
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

        // 🚨 AÇÃO CRÍTICA: Criptografar a senha antes de salvar no banco!
        String senhaEmTextoSimples = novoLogin.getSenha();
        String senhaCriptografada = passwordEncoder.encode(senhaEmTextoSimples);
        novoLogin.setSenha(senhaCriptografada); // Seta a senha criptografada

        loginRepository.save(novoLogin);

        // 4. Salvar o Atleta
        atleta.setLogin(novoLogin);
        return atletaRepository.save(atleta);
    }

    // Exemplo conceitual no AtletaService
    public Atleta update(Long id, AtletaCreateDTO dto) {
        // 1. Encontre o atleta existente (ou lance 404)
        Atleta atletaExistente = atletaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Atleta não encontrado"));

        // 2. Aplique as modificações do DTO ao objeto existente
        atletaExistente.setNome(dto.getNome());
        atletaExistente.setDataNascimento(dto.getDataNascimento());
        atletaExistente.setGenero(dto.getGenero());

        // --- Atualização do Login ---
        Login loginExistente = atletaExistente.getLogin();

        // Atualiza o email, pois ele não requer criptografia
        loginExistente.setEmail(dto.getLogin().getEmail());

        // ✅ CORREÇÃO APLICADA: Criptografa a senha SOMENTE se ela foi alterada (não for nula ou vazia)
        String novaSenha = dto.getLogin().getSenha();
        if (novaSenha != null && !novaSenha.isEmpty()) {
            String senhaCriptografada = passwordEncoder.encode(novaSenha);
            loginExistente.setSenha(senhaCriptografada);
        }
        // Se a novaSenha for nula ou vazia, a senha antiga (e criptografada) é mantida!

        // --- Atualização da Categoria ---
        // Atualiza Categoria (busca e atribui a Categoria se o ID for fornecido)
        if (dto.getIdCategoria() != null) {
            Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
                    .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
            atletaExistente.setCategoria(categoria);
        } else {
            atletaExistente.setCategoria(null); // Remove a categoria se o ID for nulo, dependendo da sua regra.
        }

        // 3. Salve e retorne (o Hibernate salva loginExistente via cascade ou implicitamente)
        return atletaRepository.save(atletaExistente);
    }

    /**
     * REGRA DE NEGÓCIO: Deletar um atleta.
     */

    public void delete(Long id) {
        Atleta atleta = findById(id); // Reusa a validação de existência

        atletaRepository.delete(atleta);
        loginRepository.delete(atleta.getLogin());
    }
}
