-- =========================================================
-- V8__insert_atleta.sql
-- Insere atletas vinculados a logins e categorias
-- =========================================================
INSERT INTO atleta (nome, data_nascimento, sexo, categoria_id, login_id)
SELECT 'Pedro Lima', '12-05-2010', 'M', c.id, l.id
FROM login l, categoria c
WHERE l.usuario = 'atleta1' AND c.nome_categoria = 'Juvenil'
  AND NOT EXISTS (SELECT 1 FROM atleta a WHERE a.login_id = l.id);

INSERT INTO atleta (nome, data_nascimento, sexo, categoria_id, login_id)
SELECT 'Ana Torres', '23-09-1995', 'F', c.id, l.id
FROM login l, categoria c
WHERE l.usuario = 'atleta2' AND c.nome_categoria = 'Adulto'
  AND NOT EXISTS (SELECT 1 FROM atleta a WHERE a.login_id = l.id);

INSERT INTO atleta (nome, data_nascimento, sexo, categoria_id, login_id)
SELECT 'Rafael Santos', '10-01-1980', 'M', c.id, l.id
FROM login l, categoria c
WHERE l.usuario = 'atleta3' AND c.nome_categoria = 'Sênior'
  AND NOT EXISTS (SELECT 1 FROM atleta a WHERE a.login_id = l.id);

INSERT INTO atleta (nome, data_nascimento, sexo, categoria_id, login_id)
SELECT 'Beatriz Gomes', '02-12-2008', 'F', c.id, l.id
FROM login l, categoria c
WHERE l.usuario = 'atleta4' AND c.nome_categoria = 'Juvenil'
  AND NOT EXISTS (SELECT 1 FROM atleta a WHERE a.login_id = l.id);
