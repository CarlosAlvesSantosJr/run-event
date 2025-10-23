-- =========================================================
-- V13__create_table_resultado.sql
-- Cria tabela resultado com vínculos a corrida e atleta
-- =========================================================
CREATE TABLE IF NOT EXISTS resultado (
    id BIGSERIAL PRIMARY KEY,
    tempo_prova TIME,
    posicaog INT,
    posicaoc INT,
    corrida_id BIGINT NOT NULL,
    atleta_id BIGINT NOT NULL,
    CONSTRAINT fk_resultado_corrida FOREIGN KEY (corrida_id)
        REFERENCES corrida(id),
    CONSTRAINT fk_resultado_atleta FOREIGN KEY (atleta_id)
        REFERENCES atleta(id)
);
