CREATE TABLE IF NOT EXISTS corrida (
    id BIGSERIAL PRIMARY KEY,
    nome_corrida VARCHAR(100) NOT NULL,
    data_corrida DATE NOT NULL,
    administrador_id BIGINT NOT NULL, -- Corrigido de INTEGER para BIGINT
    CONSTRAINT fk_corrida_admin FOREIGN KEY (administrador_id)
    REFERENCES administrador(id)
    );
