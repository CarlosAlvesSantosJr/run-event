-- =========================================================
-- V3__create_table_administrador.sql
-- Cria a tabela administrador com chave estrangeira para login
-- =========================================================
CREATE TABLE IF NOT EXISTS administrador (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    login_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_administrador_login FOREIGN KEY (login_id)
        REFERENCES login(id)
);