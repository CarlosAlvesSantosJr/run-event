CREATE TABLE administrador (
    id_adm INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    id_login INT NOT NULL UNIQUE,
    FOREIGN KEY (id_login) REFERENCES login(id_login)
);