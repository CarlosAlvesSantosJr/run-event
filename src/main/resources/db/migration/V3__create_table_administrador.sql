-- Tabela ADMINISTRADOR
CREATE TABLE administrador (
    id_adm SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    id_login INT NOT NULL UNIQUE,
    FOREIGN KEY (id_login) REFERENCES login(id_login)
);