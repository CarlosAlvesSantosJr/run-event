-- 6. Tabela PARTICIPACAO (com PK simples)
-- ==============================================
CREATE TABLE participacao (
    id_participacao INT AUTO_INCREMENT PRIMARY KEY,
    id_corrida INT NOT NULL,
    id_atleta INT NOT NULL,
    UNIQUE (id_corrida, id_atleta),
    FOREIGN KEY (id_corrida) REFERENCES corrida(id_corrida),
    FOREIGN KEY (id_atleta) REFERENCES atleta(id_atleta)
);