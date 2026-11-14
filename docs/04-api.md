# 4. Referência da API

- **URL base:** `http://localhost:8081/api`
- **Formato:** JSON (`Content-Type: application/json`)
- **Autenticação:** `Authorization: Bearer <token>` em todas as rotas, exceto as marcadas como públicas (veja [Segurança](05-seguranca.md))
- **Documentação interativa:** http://localhost:8081/swagger-ui/index.html
- **Especificação OpenAPI:** http://localhost:8081/v3/api-docs

Os exemplos abaixo foram capturados de chamadas reais à API.

## Convenções

| Item | Formato | Exemplo |
|---|---|---|
| Datas (`LocalDate`) | ISO-8601 `yyyy-MM-dd` | `"1995-05-10"` |
| Tempo de prova (`LocalTime`) | `HH:mm:ss` | `"00:48:12"` |
| Distância | número em km | `10.0` |
| Gênero | `M`, `F` ou `OUTRO` | `"F"` |
| IDs | inteiros, gerados pelo banco | `9` |

> A propriedade `spring.jackson.date-format=dd/MM/yyyy` **não** se aplica a `LocalDate`. Envie e receba datas em ISO (`yyyy-MM-dd`).

### Códigos de resposta

| Código | Quando |
|---|---|
| 200 OK | Consulta ou atualização bem-sucedida |
| 201 Created | Recurso criado (com header `Location`) |
| 204 No Content | Exclusão bem-sucedida |
| 400 Bad Request | Regra de negócio violada, recurso inexistente ou JSON inválido |
| 401 Unauthorized | Login com credenciais inválidas |
| 403 Forbidden | Requisição sem token, com token inválido ou sem o perfil exigido |
| 500 Internal Server Error | Erro inesperado |

Formato de erro:

```json
{ "timestamp": "2026-11-14T21:48:53.469351Z", "message": "Corrida não encontrada. ID: 999", "details": [] }
```

---

## Autenticação

### `POST /auth/login` (público)

```json
{ "email": "ana.demo@runevent.com", "senha": "demo1234" }
```

**200**

```json
{ "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmEuZGVtb0BydW5ldmVudC5jb20iLCJyb2xlcyI6WyJST0xFX0FETUlOIl0s..." }
```

**401**: corpo em texto `Login ou senha inválidos`.

O token carrega o e-mail (`sub`), as roles (`roles`) e expira em 24 h.

---

## Atletas: `/atletas`

| Método | Rota | Acesso | Descrição |
|---|---|---|---|
| GET | `/atletas` | `ADMIN` ou `USER` | Lista atletas |
| GET | `/atletas/{id}` | `ADMIN` ou `USER` | Busca por ID |
| POST | `/atletas` | **público** | Cadastra atleta |
| PUT | `/atletas/{id}` | `ADMIN` | Atualiza atleta (re-criptografa a senha, se enviada) |
| DELETE | `/atletas/{id}` | `ADMIN` | Remove atleta |

**POST /atletas**

```json
{
  "nome": "Ana Demo",
  "dataNascimento": "1995-05-10",
  "genero": "F",
  "login": { "email": "ana.demo@runevent.com", "senha": "demo1234" },
  "idCategoria": 2
}
```

**201** (`Location: /api/atletas/9`)

```json
{
  "idAtleta": 9,
  "nome": "Ana Demo",
  "dataNascimento": "1995-05-10",
  "genero": "F",
  "login": { "idLogin": 11, "email": "ana.demo@runevent.com", "senha": "$2a$10$ibsy...", "tipo": "ATLETA" },
  "categoria": { "idCategoria": 2, "nome": "Adulto", "descricao": "Atletas de 19 a 39 anos" },
  "roles": []
}
```

Erros: `400 "E-mail já cadastrado: ..."` e `400 "Categoria não encontrada. ID: ..."`.

---

## Administradores (organizadores): `/administradores`

| Método | Rota | Descrição |
|---|---|---|
| GET | `/administradores` | Lista |
| GET | `/administradores/{id}` | Busca por ID |
| POST | `/administradores` | Cria |
| PUT | `/administradores/{id}` | Atualiza |
| DELETE | `/administradores/{id}` | Remove |

**POST /administradores**

```json
{ "nome": "Organizadora Sul", "login": { "email": "org@runevent.com", "senha": "segredo1" } }
```

---

## Categorias: `/categorias`

| Método | Rota | Descrição |
|---|---|---|
| GET | `/categorias` | Lista |
| GET | `/categorias/{id}` | Busca por ID |
| POST | `/categorias` | Cria |
| PUT | `/categorias/{id}` | Atualiza |
| DELETE | `/categorias/{id}` | Remove |

**GET /categorias**: 200

```json
[
  { "idCategoria": 1, "nome": "Juvenil", "descricao": "Atletas até 18 anos" },
  { "idCategoria": 2, "nome": "Adulto",  "descricao": "Atletas de 19 a 39 anos" },
  { "idCategoria": 3, "nome": "Master",  "descricao": "Atletas de 40 a 59 anos" },
  { "idCategoria": 4, "nome": "Sênior",  "descricao": "Atletas acima de 60 anos" },
  { "idCategoria": 5, "nome": "Elite",   "descricao": "Categoria de alto desempenho" }
]
```

**POST /categorias**

```json
{ "nome": "Infantil", "descricao": "Atletas até 12 anos" }
```

---

## Corridas: `/corridas`

| Método | Rota | Descrição |
|---|---|---|
| GET | `/corridas` | Lista |
| GET | `/corridas/{id}` | Busca por ID |
| POST | `/corridas?idAdm={id}` | Cria corrida do organizador `idAdm` |
| PUT | `/corridas/{id}?idAdm={id}` | Atualiza |
| DELETE | `/corridas/{id}` | Remove |

**POST /corridas?idAdm=1**

```json
{ "nome": "Corrida Demo 10K", "data": "2026-12-06", "local": "Florianopolis - SC", "distancia": 10.0 }
```

**201**

```json
{
  "idCorrida": 6,
  "nome": "Corrida Demo 10K",
  "data": "2026-12-06",
  "local": "Florianopolis - SC",
  "distancia": 10.0,
  "administrador": { "idAdm": 1, "nome": "Administrador 1", "login": { "idLogin": 1, "email": "adm1@corrida.com", "senha": "123456", "tipo": "ADM" } }
}
```

Erros: `400 "Administrador não encontrado. ID: ..."` e `400 "Corrida não encontrada. ID: ..."`.

---

## Inscrições: `/participacoes`

| Método | Rota | Descrição |
|---|---|---|
| POST | `/participacoes?idAtleta={id}&idCorrida={id}` | Inscreve atleta na corrida |
| GET | `/participacoes/corrida/{idCorrida}` | Lista inscritos da corrida |
| DELETE | `/participacoes?idAtleta={id}&idCorrida={id}` | Cancela inscrição |

**POST /participacoes?idAtleta=9&idCorrida=1**: 201

```json
{
  "id": { "idCorrida": 1, "idAtleta": 9 },
  "corrida": { "idCorrida": 1, "nome": "Corrida da Primavera", "...": "..." },
  "atleta":  { "idAtleta": 9, "nome": "Ana Demo", "...": "..." }
}
```

Repetindo a mesma chamada:

```json
{ "timestamp": "2026-11-14T21:48:53.296885300Z", "message": "Atleta já inscrito nesta corrida.", "details": [] }
```

---

## Resultados: `/resultados`

| Método | Rota | Descrição |
|---|---|---|
| POST | `/resultados?idAtleta={id}&idCorrida={id}` | Registra resultado |
| GET | `/resultados/corrida/{idCorrida}` | Lista resultados da corrida |

**POST /resultados?idAtleta=9&idCorrida=1**

```json
{ "tempo": "00:48:12", "colocacaoGeral": 4, "colocacaoCategoria": 2 }
```

**201**: devolve o resultado com `idResultado`, `corrida` e `atleta` completos.

---

## Roles: `/roles`

| Método | Rota | Descrição |
|---|---|---|
| GET | `/roles` | Lista (`[{"id":1,"descricao":"ADMIN"},{"id":2,"descricao":"USER"}]`) |
| GET | `/roles/{id}` | Busca por ID |
| GET | `/roles/buscar?descricao=ADMIN` | Busca pela descrição |
| POST | `/roles` | Cria (`{"descricao": "ORGANIZADOR"}`); responde **200**, não 201 |
| PUT | `/roles/{id}` | Atualiza |
| DELETE | `/roles/{id}` | Remove |

Ainda não há endpoint para **atribuir** uma role a um atleta (veja [Limitações](08-limitacoes-e-melhorias.md)).

---

## Exemplo completo com cURL

```bash
BASE=http://localhost:8081/api

# 1. Cadastro (público)
curl -X POST $BASE/atletas -H 'Content-Type: application/json' \
  -d '{"nome":"Ana Demo","dataNascimento":"1995-05-10","genero":"F","login":{"email":"ana.demo@runevent.com","senha":"demo1234"},"idCategoria":2}'

# 2. Login
TOKEN=$(curl -s -X POST $BASE/auth/login -H 'Content-Type: application/json' \
  -d '{"email":"ana.demo@runevent.com","senha":"demo1234"}' | sed -E 's/.*"token":"([^"]+)".*/\1/')

# 3. Chamadas autenticadas
curl -H "Authorization: Bearer $TOKEN" $BASE/corridas
curl -X POST -H "Authorization: Bearer $TOKEN" "$BASE/participacoes?idAtleta=9&idCorrida=1"
```
