-- =========================================================
-- V9__create_table_corrida.sql
-- Cria tabela de corridas com vínculo ao administrador
-- =========================================================
CREATE TABLE IF NOT EXISTS corrida (
    id BIGSERIAL PRIMARY KEY,
    nome_corrida VARCHAR(100) NOT NULL,
    data_corrida DATE NOT NULL,
    administrador_id BIGINT NOT NULL,
    CONSTRAINT fk_corrida_administrador FOREIGN KEY (administrador_id)
        REFERENCES administrador(id)
);