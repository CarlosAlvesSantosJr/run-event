CREATE TABLE IF NOT EXISTS categoria (
    id BIGSERIAL PRIMARY KEY,
    nome_categoria VARCHAR(50) NOT NULL UNIQUE,
    faixa_etaria VARCHAR(20) NULL
);

CREATE TABLE IF NOT EXISTS administrador (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    login_id BIGINT NOT NULL UNIQUE, -- Corrigido de INTEGER para BIGINT
    CONSTRAINT fk_admin_login FOREIGN KEY (login_id)
        REFERENCES login(id)
);

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

CREATE TABLE IF NOT EXISTS corrida (
    id BIGSERIAL PRIMARY KEY,
    nome_corrida VARCHAR(100) NOT NULL,
    data_corrida DATE NOT NULL,
    administrador_id BIGINT NOT NULL, -- Corrigido de INTEGER para BIGINT
    CONSTRAINT fk_corrida_admin FOREIGN KEY (administrador_id)
        REFERENCES administrador(id)
);

CREATE TABLE IF NOT EXISTS corrida_categoria (
    corrida_id BIGINT NOT NULL, -- Corrigido de INTEGER para BIGINT
    categoria_id BIGINT NOT NULL, -- Corrigido de INTEGER para BIGINT
    PRIMARY KEY (corrida_id, categoria_id),
    CONSTRAINT fk_join_corrida FOREIGN KEY (corrida_id) REFERENCES corrida(id),
    CONSTRAINT fk_join_categoria FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);

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