🏃‍♂️ RunEvent — Sistema de Cadastro de Atletas Corredores
📅 Entrega 2 — Mapeamento de Entidades + Scripts Iniciais Flyway

Equipe: Carlos Alves, Edson Schmitz
🗓️ Data da entrega: 24/10/2025
🏫 Disciplina: Desenvolvimento de Sistemas Web
👨‍🏫 Professor: Clávison Zapelini

📘 Descrição do Projeto

O RunEvent é um sistema desenvolvido em Spring Boot (Java 21) para o gerenciamento de corridas de rua, com foco em cadastro de atletas, administradores, categorias, corridas e resultados.

Nesta etapa, foi implementado o mapeamento das entidades JPA e os scripts iniciais do Flyway, responsáveis por criar automaticamente o schema e as tabelas no banco de dados PostgreSQL.

🧱 Estrutura das Entidades (JPA)
Entidade	Descrição
Administrador	Representa o organizador de corridas. Relaciona-se 1:1 com Login e 1:N com Corrida.
Atleta	Representa o participante. Possui 1:1 com Login, N:1 com Categoria e 1:N com Resultado.
Categoria	Define a faixa etária dos atletas. Relaciona-se com Atleta e Corrida.
Corrida	Representa o evento em si. Relaciona-se N:N com Categoria e 1:N com Resultado.
Login	Armazena credenciais e tipo de usuário (Administrador ou Atleta).
Resultado	Guarda tempo e posições gerais e por categoria.

🧩 Enum Utilizado
public enum TipoUsuario {
    ATLETA,
    ADMINISTRADOR
}

🧰 Scripts Flyway

Os scripts estão localizados em:

src/main/resources/db/migration

🗂️ Scripts criados nesta entrega:
Versão	Arquivo	Descrição
V1	create_table_login.sql	Criação da tabela de logins
V2	insert_login.sql	Inserção de logins de atletas e administradores
V3	create_table_administrador.sql	Criação da tabela de administradores
V4	insert_administrador.sql	Inserção de administradores vinculados ao login
V5	create_table_categoria.sql	Criação da tabela de categorias
V6	insert_categoria.sql	Inserção das categorias padrão
V7	create_table_atleta.sql	Criação da tabela de atletas
V8	insert_atleta.sql	Inserção de atletas vinculados ao login e categoria
V9	create_table_corrida.sql	Criação da tabela de corridas
V10	insert_corridas.sql	Inserção das corridas iniciais
V11	create_table_corrida_categoria.sql	Tabela de relacionamento N:N entre corrida e categoria
V12	insert_corrida_categoria.sql	Inserção dos vínculos corrida ↔ categoria
V13	create_table_resultado.sql	Criação da tabela de resultados
V14	insert_resultado.sql	Inserção de resultados das corridas

🗄️ Banco de Dados

🛢️ PostgreSQL 17

Configuração (application.properties):
server.port=8081
spring.application.name=runevent

# Banco de dados PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/runevent
spring.datasource.username=postgres
spring.datasource.password= .....
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA / Hibernate
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Flyway
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
spring.flyway.baseline-on-migrate=true
spring.flyway.placeholders.schema=public
spring.flyway.baseline-version=0


🔹 ddl-auto=validate garante que o Hibernate verifica se as tabelas do Flyway estão compatíveis com as entidades JPA, sem criar nem alterar nada.

🔹 Isso assegura que o mapeamento e as migrações estejam sincronizados e consistentes.

🧾 Como executar o projeto

1️⃣ Criar o banco de dados:
CREATE DATABASE runevent;

2️⃣ Rodar o projeto:

No terminal, dentro da pasta do projeto:

mvn clean spring-boot:run


O Flyway aplicará automaticamente as migrações (V1 até V14).

🔍 Verificando no PostgreSQL

Após rodar, execute no pgAdmin ou terminal SQL:

SELECT * FROM flyway_schema_history;
SELECT table_name FROM information_schema.tables WHERE table_schema = 'public';


Isso lista todas as tabelas e confirma que as 14 migrações foram aplicadas com sucesso.

🌎 Formato de Datas

As datas foram configuradas em formato brasileiro (dd/MM/yyyy) no Spring Boot, para uso em APIs (JSON, Postman, etc.):

spring.jackson.date-format=dd/MM/yyyy
spring.jackson.locale=pt_BR
spring.jackson.time-zone=America/Sao_Paulo


⚠️ O PostgreSQL exibe datas no formato padrão YYYY-MM-DD.
Para visualizar no formato brasileiro dentro do banco, execute:

SET datestyle = "ISO, DMY";

✅ Critérios atendidos da Entrega 2

 Entidades JPA mapeadas e validadas

 Relacionamentos configurados corretamente

 Scripts Flyway (V1__...V14__) criados e testados

 Banco PostgreSQL com migrações automatizadas

 Configuração de datas e idioma para formato brasileiro

 Hibernate validando schema (ddl-auto=validate)

 Projeto executando com mvn spring-boot:run

🏁 Observações Finais

Todas as migrações e validações JPA foram executadas com sucesso.

O projeto encontra-se em conformidade com os critérios da Entrega 2 (24/10/2025).

O banco foi versionado via Flyway, com o Hibernate configurado apenas para validação de consistência.
