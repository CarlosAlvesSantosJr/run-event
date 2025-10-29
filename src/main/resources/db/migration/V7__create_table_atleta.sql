-- 4. Tabela ATLETA
-- ==============================================
CREATE TABLE atleta (
    id_atleta INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    data_nascimento DATE NOT NULL,
    genero ENUM('M', 'F', 'OUTRO'),
    id_login INT UNIQUE,
    id_categoria INT,
    FOREIGN KEY (id_login) REFERENCES login(id_login),
    FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
);