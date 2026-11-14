# 3. Modelo de dados

## Diagrama de classes (UML do domínio)

As entidades JPA do pacote `model`, com atributos, enums e multiplicidades:

```mermaid
classDiagram
    direction TB

    class Login {
        -Long idLogin
        -String email
        -String senha
        -TipoUsuario tipo
    }
    class TipoUsuario {
        <<enumeration>>
        ADM
        ATLETA
    }
    class Administrador {
        -Long idAdm
        -String nome
    }
    class Atleta {
        -Long idAtleta
        -String nome
        -LocalDate dataNascimento
        -Genero genero
    }
    class Genero {
        <<enumeration>>
        M
        F
        OUTRO
    }
    class Categoria {
        -Long idCategoria
        -String nome
        -String descricao
    }
    class Role {
        -Long id
        -String descricao
    }
    class Corrida {
        -Long idCorrida
        -String nome
        -LocalDate data
        -String local
        -Double distancia
    }
    class Participacao {
        -ParticipacaoId id
    }
    class ParticipacaoId {
        <<embeddable>>
        -Long idCorrida
        -Long idAtleta
    }
    class Resultado {
        -Long idResultado
        -LocalTime tempo
        -Integer colocacaoGeral
        -Integer colocacaoCategoria
    }

    Login --> TipoUsuario
    Atleta --> Genero
    Administrador "1" --> "1" Login : login
    Atleta "1" --> "1" Login : login
    Atleta "*" --> "0..1" Categoria : categoria
    Atleta "*" --> "*" Role : roles (EAGER)
    Corrida "*" --> "1" Administrador : organizada por
    Participacao *-- ParticipacaoId : @EmbeddedId
    Participacao "*" --> "1" Atleta
    Participacao "*" --> "1" Corrida
    Resultado "*" --> "1" Atleta
    Resultado "*" --> "1" Corrida
```

### Notas do mapeamento

- **Login separado do usuário**: `Atleta` e `Administrador` têm um `Login` próprio (1:1), que guarda credenciais e tipo. O e-mail é único na tabela `login`, então um mesmo e-mail não serve para atleta e administrador ao mesmo tempo.
- **Inscrição com chave composta**: `Participacao` usa `@EmbeddedId ParticipacaoId(idCorrida, idAtleta)` com `@MapsId`. É a chave primária que garante uma inscrição por atleta e corrida.
- **Roles**: relação N:N `Atleta`↔`Role` pela tabela `atleta_roles`, carregada de forma *eager* porque é usada a cada requisição autenticada.
- **Resultado** é independente da inscrição: referencia atleta e corrida diretamente.

## Diagrama entidade-relacionamento (banco)

Tabelas criadas pelas migrações Flyway:

```mermaid
erDiagram
    LOGIN {
        serial id_login PK
        varchar(100) email UK
        varchar(100) senha
        varchar(10) tipo "ADM | ATLETA"
    }
    ADMINISTRADOR {
        serial id_adm PK
        varchar(100) nome
        int id_login FK,UK
    }
    CATEGORIA {
        serial id_categoria PK
        varchar(50) nome
        varchar(255) descricao
    }
    ATLETA {
        serial id_atleta PK
        varchar(100) nome
        date data_nascimento
        varchar(10) genero "M | F | OUTRO"
        int id_login FK,UK
        int id_categoria FK "nullable"
    }
    CORRIDA {
        serial id_corrida PK
        varchar(100) nome
        date data
        varchar(150) local
        double distancia
        int id_adm FK
    }
    PARTICIPACAO {
        int id_corrida PK,FK
        int id_atleta PK,FK
    }
    RESULTADO {
        serial id_resultado PK
        int id_corrida FK
        int id_atleta FK
        time tempo
        int colocacao_geral
        int colocacao_categoria
    }
    ROLE {
        serial id PK
        varchar(100) descricao
    }
    ATLETA_ROLES {
        int atleta_id
        int role_id
    }

    LOGIN ||--o| ADMINISTRADOR : "autentica"
    LOGIN ||--o| ATLETA : "autentica"
    CATEGORIA |o--o{ ATLETA : "classifica"
    ADMINISTRADOR ||--o{ CORRIDA : "organiza"
    CORRIDA ||--o{ PARTICIPACAO : "recebe"
    ATLETA ||--o{ PARTICIPACAO : "faz"
    CORRIDA ||--o{ RESULTADO : "gera"
    ATLETA ||--o{ RESULTADO : "obtém"
    ATLETA ||--o{ ATLETA_ROLES : "possui"
    ROLE ||--o{ ATLETA_ROLES : "concede"
```

> A tabela `atleta_roles` não tem chave primária nem chaves estrangeiras declaradas no SQL (`V17`). A integridade depende só da aplicação.

## Migrações Flyway

Ficam em `src/main/resources/db/migration` e são aplicadas em ordem na inicialização. O Hibernate está com `ddl-auto=none`, então **toda mudança de schema precisa de uma nova migração** (`V19__...sql`). Nunca edite uma migração já aplicada.

| Versão | Arquivo | O que faz |
|---|---|---|
| V1 | `create_table_login.sql` | Tabela `login` |
| V2 | `insert_login.sql` | 10 logins de exemplo (2 ADM, 8 ATLETA) |
| V3 | `create_table_administrador.sql` | Tabela `administrador` |
| V4 | `insert_administrador.sql` | 2 administradores |
| V5 | `create_table_categoria.sql` | Tabela `categoria` |
| V6 | `insert_categoria.sql` | 5 categorias |
| V7 | `create_table_atleta.sql` | Tabela `atleta` |
| V8 | `insert_atleta.sql` | 8 atletas |
| V9 | `create_table_corrida.sql` | Tabela `corrida` |
| V10 | `insert_corridas.sql` | 5 corridas |
| V11 | `create_table_participacao.sql` | Tabela `participacao` (chave composta) |
| V12 | `insert_participacao.sql` | 8 inscrições |
| V13 | `create_table_resultado.sql` | Tabela `resultado` |
| V14 | `insert_resultado.sql` | 8 resultados |
| V15 | `create_roles.sql` | Tabela `role` |
| V16 | `insert_roles.sql` | Roles `ADMIN` e `USER` |
| V17 | `atleta_roles.sql` | Tabela de junção `atleta_roles` |
| V18 | `inserir_administrador.sql` | Dá a role `ADMIN` ao atleta 1 |

## Dados iniciais

| Tabela | Registros | Observação |
|---|---|---|
| `categoria` | 5 | Juvenil (até 18), Adulto (19–39), Master (40–59), Sênior (60+), Elite |
| `administrador` | 2 | `adm1@corrida.com`, `adm2@corrida.com` |
| `atleta` | 8 | Só o atleta 1 (`clavison@gmail.com`) tem senha em BCrypt e role `ADMIN` |
| `corrida` | 5 | Ex.: Corrida da Primavera (5 km), Maratona da Cidade (42,2 km) |
| `participacao` / `resultado` | 8 / 8 | Inscrições e resultados de exemplo |

> As senhas de exemplo dos outros atletas (`senha123`) e dos administradores (`123456`) estão em **texto puro** no banco. Como o login compara as senhas com BCrypt, esses usuários não conseguem autenticar. Para testar, cadastre um atleta novo pela API (veja [Telas](06-telas.md)).
