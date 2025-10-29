-- 7. Tabela RESULTADO
-- ==============================================
CREATE TABLE resultado (
    id_resultado INT AUTO_INCREMENT PRIMARY KEY,
    id_corrida INT NOT NULL,
    id_atleta INT NOT NULL,
    tempo TIME,
    colocacao_geral INT,
    colocacao_categoria INT,
    FOREIGN KEY (id_corrida) REFERENCES corrida(id_corrida),
    FOREIGN KEY (id_atleta) REFERENCES atleta(id_atleta)
);