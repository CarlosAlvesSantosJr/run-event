-- =========================================================
-- V5__create_table_categoria.sql
-- Cria a tabela categoria de corrida
-- =========================================================
CREATE TABLE IF NOT EXISTS categoria (
    id BIGSERIAL PRIMARY KEY,
    nome_categoria VARCHAR(50) NOT NULL UNIQUE,
    faixa_etaria VARCHAR(20)
);