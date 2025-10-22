CREATE TABLE IF NOT EXISTS atleta (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    data_nascimento DATE NOT NULL,
    sexo VARCHAR(1) NOT NULL,
    categoria_id BIGINT NULL, -- Corrigido de INTEGER para BIGINT
    login_id BIGINT NOT NULL UNIQUE, -- Corrigido de INTEGER para BIGINT
    CONSTRAINT fk_atleta_categoria FOREIGN KEY (categoria_id)
    REFERENCES categoria(id),
    CONSTRAINT fk_atleta_login FOREIGN KEY (login_id)
    REFERENCES login(id)
    );
