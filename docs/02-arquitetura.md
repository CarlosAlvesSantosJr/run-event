# 2. Arquitetura

## Stack

| Camada | Tecnologia | Versão |
|---|---|---|
| Linguagem | Java | 21 |
| Framework | Spring Boot | 3.2.5 |
| Web | Spring Web MVC (Tomcat embutido) | — |
| Persistência | Spring Data JPA / Hibernate | 6.4 |
| Banco de dados | PostgreSQL | 17 (testado) |
| Migrações | Flyway | 10.17 |
| Segurança | Spring Security + jjwt | 0.11.5 |
| Documentação | springdoc-openapi (Swagger UI) | 2.5.0 |
| Utilitários | Lombok | — |
| Testes | JUnit 5, Mockito, MockMvc | — |
| Build | Maven (wrapper incluso) | — |

## Visão em camadas

A aplicação segue a arquitetura em camadas tradicional do Spring: cada camada só depende da camada logo abaixo.

```mermaid
flowchart TB
    Client["Cliente HTTP<br/>(app mobile, front-end, Postman, Swagger UI)"]

    subgraph App["RunEvent API (Spring Boot, porta 8081)"]
        direction TB
        subgraph Sec["Segurança"]
            F["JwtAuthFilter"]
            SC["SecurityConfig<br/>(regras de acesso)"]
        end
        subgraph Web["Camada web: controller"]
            C["*Controller<br/>(REST, /api/...)"]
            EH["GlobalExceptionHandler"]
            DTO["DTOs e Mappers"]
        end
        subgraph Biz["Camada de negócio: service"]
            S["*Service<br/>(regras e validações)"]
        end
        subgraph Data["Camada de dados: repository"]
            R["*Repository<br/>(Spring Data JPA)"]
            M["Entidades JPA (model)"]
        end
    end

    DB[("PostgreSQL<br/>schema versionado pelo Flyway")]

    Client -->|"HTTP + JSON<br/>Authorization: Bearer"| F
    F --> SC --> C
    C --> DTO
    C --> S
    C -. exceções .-> EH
    S --> R
    R --> M
    R -->|JDBC| DB
```

## Pacotes

```
com.example.runevent
├── RuneventApplication.java   # ponto de entrada (@SpringBootApplication)
├── config/                    # SecurityConfig, JwtFilterConfig, CorsConfig, SwaggerConfig
├── controller/                # 8 controllers REST
├── dto/                       # objetos de entrada/saída
│   └── mapper/                # conversão entidade ↔ DTO
├── exeptions/                 # GlobalExceptionHandler e ApiExeption (formato de erro)
├── model/                     # 9 entidades JPA (inclui a chave composta ParticipacaoId)
├── repository/                # 8 interfaces Spring Data JPA
├── security/                  # JwtUtil (gera/valida token) e JwtAuthFilter
└── service/                   # 8 services com as regras de negócio
```

## Componentes (diagrama de classes das camadas)

As dependências entre controllers, services e repositories:

```mermaid
classDiagram
    direction LR

    class AuthController
    class AtletaController
    class AdministradorController
    class CategoriaController
    class CorridaController
    class ParticipacaoController
    class ResultadoController
    class RoleController

    class AtletaService {
        +findAll() List~Atleta~
        +findById(Long) Atleta
        +create(Atleta) Atleta
        +update(Long, AtletaCreateDTO) Atleta
        +delete(Long)
    }
    class AdministradorService {
        +findAll() List~Administrador~
        +findById(Long) Administrador
        +create(Administrador) Administrador
        +update(Long, AdministradorCreateDTO) Administrador
        +delete(Long)
    }
    class CategoriaService {
        +findAll() List~Categoria~
        +findById(Long) Categoria
        +create(Categoria) Categoria
        +update(Long, Categoria) Categoria
        +delete(Long)
    }
    class CorridaService {
        +findAll() List~Corrida~
        +findById(Long) Corrida
        +create(Corrida, Long idAdm) Corrida
        +update(Long, CorridaCreateDTO, Long idAdm) Corrida
        +delete(Long)
    }
    class ParticipacaoService {
        +create(Long idAtleta, Long idCorrida) Participacao
        +findByCorrida(Long) List~Participacao~
        +delete(Long idAtleta, Long idCorrida)
    }
    class ResultadoService {
        +create(Resultado, Long idAtleta, Long idCorrida) Resultado
        +findByCorrida(Long) List~Resultado~
        +findById(Long) Resultado
    }
    class RoleService {
        +listarTodas() List~RoleDto~
        +buscarPorId(Long) RoleDto
        +buscarPordescricao(String) RoleDto
        +criar(Role) RoleDto
        +atualizar(Long, Role) RoleDto
        +deletar(Long)
    }
    class LoginService {
        +validateEmailUniqueness(String)
    }
    class JwtUtil {
        +generateToken(UserDetails) String
        +validateToken(String, UserDetails) Boolean
        +extractUsername(String) String
    }

    AuthController --> JwtUtil
    AuthController ..> UserDetailsService
    AtletaController --> AtletaService
    AdministradorController --> AdministradorService
    CategoriaController --> CategoriaService
    CorridaController --> CorridaService
    ParticipacaoController --> ParticipacaoService
    ResultadoController --> ResultadoService
    RoleController --> RoleService

    AtletaService --> LoginService
    AtletaService ..> PasswordEncoder
    AdministradorService --> LoginService
    ParticipacaoService --> AtletaService
    ParticipacaoService --> CorridaService
    ResultadoService --> AtletaService
    ResultadoService --> CorridaService

    AtletaService --> AtletaRepository
    AtletaService --> CategoriaRepository
    AtletaService --> LoginRepository
    AdministradorService --> AdministradorRepository
    CategoriaService --> CategoriaRepository
    CorridaService --> CorridaRepository
    CorridaService --> AdministradorRepository
    ParticipacaoService --> ParticipacaoRepository
    ResultadoService --> ResultadoRepository
    RoleService --> RoleRepository
    LoginService --> LoginRepository
```

## Fluxo de uma requisição

Exemplo: inscrição de um atleta em uma corrida (`POST /api/participacoes?idAtleta=9&idCorrida=1`).

```mermaid
sequenceDiagram
    autonumber
    actor U as Cliente
    participant F as JwtAuthFilter
    participant SC as SecurityFilterChain
    participant C as ParticipacaoController
    participant S as ParticipacaoService
    participant AS as AtletaService
    participant CS as CorridaService
    participant R as ParticipacaoRepository
    participant DB as PostgreSQL
    participant EH as GlobalExceptionHandler

    U->>F: POST /api/participacoes + Bearer token
    F->>F: valida o JWT e carrega o usuário
    F->>SC: SecurityContext autenticado
    SC->>C: rota liberada (usuário autenticado)
    C->>S: create(idAtleta, idCorrida)
    S->>AS: findById(idAtleta)
    S->>CS: findById(idCorrida)
    S->>R: findById(ParticipacaoId)
    R->>DB: SELECT
    alt já inscrito
        S-->>EH: RuntimeException("Atleta já inscrito nesta corrida.")
        EH-->>U: 400 {timestamp, message, details}
    else nova inscrição
        S->>R: save(participacao)
        R->>DB: INSERT
        C-->>U: 201 Created + JSON da participação
    end
```

## Tratamento de erros

Toda exceção que sai de um controller é capturada pelo `GlobalExceptionHandler` (`@ControllerAdvice`) e devolvida neste formato:

```json
{
  "timestamp": "2026-11-14T21:48:53.296885300Z",
  "message": "Atleta já inscrito nesta corrida.",
  "details": []
}
```

| Exceção | Status HTTP |
|---|---|
| `MethodArgumentNotValidException` (validação do corpo) | 400, com `details` campo a campo |
| `RuntimeException` (regras de negócio, recurso não encontrado) | **400** |
| Qualquer outra `Exception` | 500 |

> Por isso, "não encontrado" responde **400**, e não 404, apesar de algumas anotações do Swagger citarem 404.

## Configuração

Todas as propriedades ficam em `src/main/resources/application.properties`.

| Propriedade | Valor padrão | Sobrescrever com |
|---|---|---|
| `server.port` | `8081` | `SERVER_PORT` |
| `spring.datasource.url` | `jdbc:postgresql://localhost:5432/runevent` | `DB_URL` |
| `spring.datasource.username` | `postgres` | `DB_USERNAME` |
| `spring.datasource.password` | `carlos` | `DB_PASSWORD` |
| `spring.jpa.hibernate.ddl-auto` | `none` | — |
| `jwt.secret` | chave padrão embutida em `JwtUtil` | `JWT_SECRET` |
| `jwt.expiration` | `86400000` (24 h) | `JWT_EXPIRATION` |

O Spring Boot converte variáveis de ambiente em propriedades (`JWT_SECRET` → `jwt.secret`). Por isso as linhas sem variável própria no arquivo também podem ser sobrescritas.

## Implantação

```mermaid
flowchart LR
    subgraph Cliente
        B["Navegador<br/>Swagger UI"]
        M["App mobile / front-end"]
    end
    subgraph Servidor["Servidor da aplicação"]
        J["runevent.jar<br/>JDK 21 · porta 8081"]
    end
    subgraph Banco["Servidor de banco"]
        P[("PostgreSQL<br/>database runevent<br/>porta 5432")]
    end
    B -->|HTTP| J
    M -->|HTTP/JSON + JWT| J
    J -->|JDBC| P
```

Gerar o artefato executável:

```bash
./mvnw clean package -DskipTests
java -jar target/runevent-0.0.1-SNAPSHOT.jar
```

Na primeira inicialização, o Flyway cria o schema e carrega os dados de exemplo.
