-- =========================================================
-- V2__insert_login.sql
-- Insere logins iniciais de atletas e administradores
-- =========================================================
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Inserir logins de atletas
INSERT INTO login (usuario, senha, tipo_usuario)
SELECT 'atleta1', crypt('user123', gen_salt('bf')), 'ATLETA'
WHERE NOT EXISTS (SELECT 1 FROM login WHERE usuario = 'atleta1');

INSERT INTO login (usuario, senha, tipo_usuario)
SELECT 'atleta2', crypt('user456', gen_salt('bf')), 'ATLETA'
WHERE NOT EXISTS (SELECT 1 FROM login WHERE usuario = 'atleta2');

INSERT INTO login (usuario, senha, tipo_usuario)
SELECT 'atleta3', crypt('user789', gen_salt('bf')), 'ATLETA'
WHERE NOT EXISTS (SELECT 1 FROM login WHERE usuario = 'atleta3');

INSERT INTO login (usuario, senha, tipo_usuario)
SELECT 'atleta4', crypt('user000', gen_salt('bf')), 'ATLETA'
WHERE NOT EXISTS (SELECT 1 FROM login WHERE usuario = 'atleta4');

-- Inserir logins de administradores
INSERT INTO login (usuario, senha, tipo_usuario)
SELECT 'admin_carlos', crypt('admin123', gen_salt('bf')), 'ADMINISTRADOR'
WHERE NOT EXISTS (SELECT 1 FROM login WHERE usuario = 'admin_carlos');

INSERT INTO login (usuario, senha, tipo_usuario)
SELECT 'admin_fernanda', crypt('admin456', gen_salt('bf')), 'ADMINISTRADOR'
WHERE NOT EXISTS (SELECT 1 FROM login WHERE usuario = 'admin_fernanda');

INSERT INTO login (usuario, senha, tipo_usuario)
SELECT 'admin_joao', crypt('admin789', gen_salt('bf')), 'ADMINISTRADOR'
WHERE NOT EXISTS (SELECT 1 FROM login WHERE usuario = 'admin_joao');

INSERT INTO login (usuario, senha, tipo_usuario)
SELECT 'admin_mariana', crypt('admin000', gen_salt('bf')), 'ADMINISTRADOR'
WHERE NOT EXISTS (SELECT 1 FROM login WHERE usuario = 'admin_mariana');
