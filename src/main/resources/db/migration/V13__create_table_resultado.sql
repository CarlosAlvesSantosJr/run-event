-- Tabela RESULTADO
CREATE TABLE resultado (
    id_resultado SERIAL PRIMARY KEY,
    id_corrida INT NOT NULL,
    id_atleta INT NOT NULL,
    tempo TIME NOT NULL,
    colocacao_geral INT NOT NULL,
    colocacao_categoria INT NOT NULL,
    FOREIGN KEY (id_corrida) REFERENCES corrida(id_corrida),
    FOREIGN KEY (id_atleta) REFERENCES atleta(id_atleta)
);