CREATE TABLE IF NOT EXISTS resultado (
    id BIGSERIAL PRIMARY KEY,
    tempo_prova TIME NULL,
    posicaog INT,
    posicaoc INT,
    corrida_id BIGINT NOT NULL, -- Corrigido de INTEGER para BIGINT
    atleta_id BIGINT NOT NULL, -- Corrigido de INTEGER para BIGINT
    CONSTRAINT fk_resultado_corrida FOREIGN KEY (corrida_id)
    REFERENCES corrida(id),
    CONSTRAINT fk_resultado_atleta FOREIGN KEY (atleta_id)
    REFERENCES atleta(id)
    );
