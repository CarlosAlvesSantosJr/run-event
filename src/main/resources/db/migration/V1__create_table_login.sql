CREATE TABLE IF NOT EXISTS login (
    id BIGSERIAL PRIMARY KEY,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    tipo_usuario VARCHAR(20) NOT NULL
    CONSTRAINT chk_tipo_usuario
    CHECK (tipo_usuario IN ('ATLETA', 'ADMINISTRADOR'))
    );

-- A constraint (restrição) 'chk_tipo_usuario' garante que o valor do campo 'tipo_usuario'
-- só possa ser um dos dois valores permitidos: 'ATLETA' ou 'ADMINISTRADOR'.
-- Caso alguém tente inserir outro valor, o banco rejeita a operação.