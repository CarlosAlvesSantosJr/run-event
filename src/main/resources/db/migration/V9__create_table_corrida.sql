-- Tabela CORRIDA
CREATE TABLE corrida (
  id_corrida SERIAL PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  data DATE NOT NULL,
  local VARCHAR(150) NOT NULL,
  distancia DOUBLE PRECISION NOT NULL,
  id_adm INT NOT NULL,
  FOREIGN KEY (id_adm) REFERENCES administrador(id_adm)
);