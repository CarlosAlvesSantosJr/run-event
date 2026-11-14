# 8. Limitações conhecidas e melhorias sugeridas

Os itens abaixo foram confirmados no código e com a API rodando. Estão ordenados por impacto.

## Segurança

| # | Problema | Impacto | Sugestão |
|---|---|---|---|
| S1 | **Respostas expõem senhas.** As entidades são devolvidas direto no JSON, e o `login.senha` vai junto: hash BCrypt dos atletas e senha em texto puro dos administradores, inclusive aninhada em corridas, inscrições e resultados. | Alto | Responder com DTOs de saída sem a senha, ou marcar o campo com `@JsonProperty(access = WRITE_ONLY)` |
| S2 | **Senha de administrador sem criptografia.** `AdministradorService.create` salva a senha como recebida. | Alto | Aplicar `PasswordEncoder.encode` como em `AtletaService` |
| S3 | **Autorização só em `/api/atletas`.** Qualquer usuário autenticado pode criar e excluir corridas, categorias, organizadores, resultados e roles. | Alto | Restringir escrita a `ADMIN` no `SecurityConfig` ou com `@PreAuthorize` |
| S4 | **Chave JWT padrão no código-fonte** (`JwtUtil`). | Médio | Exigir `JWT_SECRET` por variável de ambiente, sem valor padrão |
| S5 | **Senha do banco no `application.properties`** como valor padrão. | Médio | Remover o padrão e exigir `DB_PASSWORD` |
| S6 | **CORS liberado para `*`.** | Baixo | Restringir aos domínios dos clientes |

## Funcionalidade

| # | Problema | Sugestão |
|---|---|---|
| F1 | Organizadores (`Administrador`) não conseguem fazer login: o `UserDetailsService` só procura atletas. | Buscar pelo `Login`, considerando o `tipo` |
| F2 | Atletas cadastrados pela API não recebem role, então não acessam `GET /api/atletas`. | Atribuir `USER` por padrão no cadastro |
| F3 | Não há endpoint para atribuir roles a atletas. | Ex.: `POST /api/atletas/{id}/roles/{idRole}` (restrito a `ADMIN`) |
| F4 | Os atletas e administradores de exemplo (exceto o atleta 1) têm senhas em texto puro e não conseguem logar. | Migração `V19` gravando as senhas em BCrypt |
| F5 | Nada impede dois resultados para o mesmo atleta na mesma corrida, nem resultado sem inscrição. | Constraint `UNIQUE (id_corrida, id_atleta)` e validação no service |
| F6 | `atleta_roles` não tem chave primária nem FKs. | Migração adicionando `PRIMARY KEY (atleta_id, role_id)` e FKs |
| F7 | Não há paginação nas listagens. | `Pageable` nos endpoints `GET` de coleção |

## Qualidade da API

| # | Problema | Sugestão |
|---|---|---|
| Q1 | A validação de entrada não funciona: `spring-boot-starter-validation` está comentado no `pom.xml`, então `@Valid`, `@NotBlank` e afins são ignorados. | Descomentar a dependência |
| Q2 | "Não encontrado" responde **400**, e não 404, porque toda `RuntimeException` vira 400. | Exceção própria (`RecursoNaoEncontradoException`) mapeada para 404 |
| Q3 | Sem token, a API responde 403 em vez de 401. | Configurar um `AuthenticationEntryPoint` que responda 401 |
| Q4 | `spring.jackson.date-format=dd/MM/yyyy` não vale para `LocalDate`, que usa ISO. | Remover a propriedade ou usar `@JsonFormat` nos campos |
| Q5 | `POST /api/roles` responde 200 em vez de 201. | Usar `ResponseEntity.created(...)` |
| Q6 | Tags do Swagger misturam nomes (`Administradores` e `*-controller`). | `@Tag(name = "...")` em todos os controllers |
| Q7 | O filtro `JwtAuthFilter` é registrado por dois beans (`SecurityConfig` e `JwtFilterConfig`). | Manter só um |
| Q8 | O pacote se chama `exeptions` (grafia incorreta). | Renomear para `exceptions` |
| Q9 | `JwtUtil` usa `System.out.println` para log. | SLF4J (`log.debug`) |

## Infraestrutura

- `docker-compose.yml` com PostgreSQL e a API, para subir o ambiente com um comando.
- Testes de integração com Testcontainers, para não depender de um PostgreSQL local.
- Pipeline de CI (GitHub Actions) rodando `./mvnw test` a cada push.
- Relatório de cobertura com JaCoCo no build do Maven, sem depender da IDE.
