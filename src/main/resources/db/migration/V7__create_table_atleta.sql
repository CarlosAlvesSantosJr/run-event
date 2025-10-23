-- =========================================================
-- V7__create_table_atleta.sql
-- Cria a tabela atleta com chaves estrangeiras
-- =========================================================
CREATE TABLE IF NOT EXISTS atleta (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    data_nascimento DATE NOT NULL,
    sexo VARCHAR(1) NOT NULL,
    categoria_id BIGINT,
    login_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_atleta_categoria FOREIGN KEY (categoria_id)
        REFERENCES categoria(id),
    CONSTRAINT fk_atleta_login FOREIGN KEY (login_id)
        REFERENCES login(id)
);
