ALTER TABLE login
ADD CONSTRAINT chk_tipo_usuario
CHECK (tipo_usuario IN ('ATLETA', 'ADMINISTRADOR'));