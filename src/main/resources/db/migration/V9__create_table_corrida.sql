-- 5. Tabela CORRIDA
-- ==============================================
CREATE TABLE corrida (
    id_corrida INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    data DATE NOT NULL,
    local VARCHAR(150) NOT NULL,
    distancia DECIMAL(5,2) NOT NULL,
    id_adm INT NOT NULL,
    FOREIGN KEY (id_adm) REFERENCES adm(id_adm)
);