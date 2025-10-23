-- =========================================================
-- V6__insert_categoria.sql
-- Insere categorias padrão
-- =========================================================
INSERT INTO categoria (nome_categoria, faixa_etaria)
SELECT 'Infantil', '6-11'
WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nome_categoria = 'Infantil');

INSERT INTO categoria (nome_categoria, faixa_etaria)
SELECT 'Juvenil', '12-17'
WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nome_categoria = 'Juvenil');

INSERT INTO categoria (nome_categoria, faixa_etaria)
SELECT 'Adulto', '18-39'
WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nome_categoria = 'Adulto');

INSERT INTO categoria (nome_categoria, faixa_etaria)
SELECT 'Sênior', '40+'
WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nome_categoria = 'Sênior');
