-- =========================================================
-- V11__create_table_corrida_categoria.sql
-- Cria tabela de junção corrida-categoria
-- =========================================================
CREATE TABLE IF NOT EXISTS corrida_categoria (
    corrida_id BIGINT NOT NULL,
    categoria_id BIGINT NOT NULL,
    PRIMARY KEY (corrida_id, categoria_id),
    CONSTRAINT fk_corrida_categoria_corrida FOREIGN KEY (corrida_id)
        REFERENCES corrida(id),
    CONSTRAINT fk_corrida_categoria_categoria FOREIGN KEY (categoria_id)
        REFERENCES categoria(id)
);
