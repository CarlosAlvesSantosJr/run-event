-- Tabela ATLETA
CREATE TABLE atleta (
    id_atleta SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    data_nascimento DATE NOT NULL,
    genero VARCHAR(10) NOT NULL CHECK (genero IN ('M', 'F', 'OUTRO')),
    id_login INT UNIQUE NOT NULL,
    id_categoria INT,
    FOREIGN KEY (id_login) REFERENCES login(id_login),
    FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
);