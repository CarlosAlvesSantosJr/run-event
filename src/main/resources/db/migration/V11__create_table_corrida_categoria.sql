CREATE TABLE IF NOT EXISTS corrida_categoria (
    corrida_id BIGINT NOT NULL, -- Corrigido de INTEGER para BIGINT
    categoria_id BIGINT NOT NULL, -- Corrigido de INTEGER para BIGINT
    PRIMARY KEY (corrida_id, categoria_id),
    CONSTRAINT fk_join_corrida FOREIGN KEY (corrida_id) REFERENCES corrida(id),
    CONSTRAINT fk_join_categoria FOREIGN KEY (categoria_id) REFERENCES categoria(id)
    );
