
-- 1. Tabela LOGIN
-- ==============================================
CREATE TABLE login (
    id_login INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    tipo ENUM('ADM', 'ATLETA') NOT NULL
);