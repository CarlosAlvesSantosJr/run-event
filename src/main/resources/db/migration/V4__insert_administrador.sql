-- =========================================================
-- V4__insert_administrador.sql
-- Insere administradores vinculados a logins existentes
-- =========================================================
INSERT INTO administrador (nome, login_id)
SELECT 'Carlos Silva', l.id FROM login l
WHERE l.usuario = 'admin_carlos'
  AND NOT EXISTS (SELECT 1 FROM administrador a WHERE a.login_id = l.id);

INSERT INTO administrador (nome, login_id)
SELECT 'Fernanda Souza', l.id FROM login l
WHERE l.usuario = 'admin_fernanda'
  AND NOT EXISTS (SELECT 1 FROM administrador a WHERE a.login_id = l.id);

INSERT INTO administrador (nome, login_id)
SELECT 'João Pereira', l.id FROM login l
WHERE l.usuario = 'admin_joao'
  AND NOT EXISTS (SELECT 1 FROM administrador a WHERE a.login_id = l.id);

INSERT INTO administrador (nome, login_id)
SELECT 'Mariana Costa', l.id FROM login l
WHERE l.usuario = 'admin_mariana'
  AND NOT EXISTS (SELECT 1 FROM administrador a WHERE a.login_id = l.id);
