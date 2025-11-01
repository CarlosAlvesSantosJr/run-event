-- Tabela PARTICIPACAO (chave composta)
CREATE TABLE participacao (
    id_corrida INT NOT NULL,
    id_atleta INT NOT NULL,
    PRIMARY KEY (id_corrida, id_atleta),
    FOREIGN KEY (id_corrida) REFERENCES corrida(id_corrida),
    FOREIGN KEY (id_atleta) REFERENCES atleta(id_atleta)
);