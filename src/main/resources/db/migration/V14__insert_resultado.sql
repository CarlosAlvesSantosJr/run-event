-- =========================================================
-- V14__insert_resultado.sql
-- Insere resultados de atletas em corridas
-- =========================================================
INSERT INTO resultado (tempo_prova, posicaog, posicaoc, corrida_id, atleta_id)
SELECT '00:25:32', 1, 1, c.id, a.id
FROM corrida c, atleta a
WHERE c.nome_corrida = 'Corrida da Primavera' AND a.nome = 'Pedro Lima'
  AND NOT EXISTS (SELECT 1 FROM resultado r WHERE r.corrida_id = c.id AND r.atleta_id = a.id);

INSERT INTO resultado (tempo_prova, posicaog, posicaoc, corrida_id, atleta_id)
SELECT '00:45:10', 2, 1, c.id, a.id
FROM corrida c, atleta a
WHERE c.nome_corrida = 'Desafio das Montanhas' AND a.nome = 'Ana Torres'
  AND NOT EXISTS (SELECT 1 FROM resultado r WHERE r.corrida_id = c.id AND r.atleta_id = a.id);

INSERT INTO resultado (tempo_prova, posicaog, posicaoc, corrida_id, atleta_id)
SELECT '01:20:15', 3, 1, c.id, a.id
FROM corrida c, atleta a
WHERE c.nome_corrida = 'Maratona Cidade Azul' AND a.nome = 'Rafael Santos'
  AND NOT EXISTS (SELECT 1 FROM resultado r WHERE r.corrida_id = c.id AND r.atleta_id = a.id);

INSERT INTO resultado (tempo_prova, posicaog, posicaoc, corrida_id, atleta_id)
SELECT '00:22:47', 1, 1, c.id, a.id
FROM corrida c, atleta a
WHERE c.nome_corrida = 'Corrida Solidária' AND a.nome = 'Beatriz Gomes'
  AND NOT EXISTS (SELECT 1 FROM resultado r WHERE r.corrida_id = c.id AND r.atleta_id = a.id);
