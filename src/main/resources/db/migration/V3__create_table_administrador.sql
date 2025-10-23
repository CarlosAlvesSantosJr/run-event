CREATE TABLE IF NOT EXISTS administrador (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    login_id BIGINT NOT NULL UNIQUE, -- Corrigido de INTEGER para BIGINT
    CONSTRAINT fk_admin_login FOREIGN KEY (login_id)
    REFERENCES login(id)
    );