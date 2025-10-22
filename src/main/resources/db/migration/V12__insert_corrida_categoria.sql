-- =========================================================
-- V12__insert_corrida_categoria.sql
-- Relaciona corridas com categorias participantes
-- =========================================================
INSERT INTO corrida_categoria (corrida_id, categoria_id)
SELECT c.id, cat.id FROM corrida c, categoria cat
WHERE c.nome_corrida = 'Corrida da Primavera' AND cat.nome_categoria IN ('Juvenil', 'Adulto')
  AND NOT EXISTS (
    SELECT 1 FROM corrida_categoria cc
    WHERE cc.corrida_id = c.id AND cc.categoria_id = cat.id
  );

INSERT INTO corrida_categoria (corrida_id, categoria_id)
SELECT c.id, cat.id FROM corrida c, categoria cat
WHERE c.nome_corrida = 'Desafio das Montanhas' AND cat.nome_categoria IN ('Adulto', 'Sênior')
  AND NOT EXISTS (
    SELECT 1 FROM corrida_categoria cc
    WHERE cc.corrida_id = c.id AND cc.categoria_id = cat.id
  );

INSERT INTO corrida_categoria (corrida_id, categoria_id)
SELECT c.id, cat.id FROM corrida c, categoria cat
WHERE c.nome_corrida = 'Maratona Cidade Azul' AND cat.nome_categoria IN ('Adulto')
  AND NOT EXISTS (
    SELECT 1 FROM corrida_categoria cc
    WHERE cc.corrida_id = c.id AND cc.categoria_id = cat.id
  );

INSERT INTO corrida_categoria (corrida_id, categoria_id)
SELECT c.id, cat.id FROM corrida c, categoria cat
WHERE c.nome_corrida = 'Corrida Solidária' AND cat.nome_categoria IN ('Infantil', 'Juvenil')
  AND NOT EXISTS (
    SELECT 1 FROM corrida_categoria cc
    WHERE cc.corrida_id = c.id AND cc.categoria_id = cat.id
  );