-- =========================================================
-- V10__insert_corridas.sql
-- Insere corridas associadas a administradores
-- =========================================================
INSERT INTO corrida (nome_corrida, data_corrida, administrador_id)
SELECT 'Corrida da Primavera', '15-09-2025', a.id FROM administrador a
WHERE a.nome = 'Carlos Silva'
  AND NOT EXISTS (SELECT 1 FROM corrida c WHERE c.nome_corrida = 'Corrida da Primavera');

INSERT INTO corrida (nome_corrida, data_corrida, administrador_id)
SELECT 'Desafio das Montanhas', '01-10-2025', a.id FROM administrador a
WHERE a.nome = 'Fernanda Souza'
  AND NOT EXISTS (SELECT 1 FROM corrida c WHERE c.nome_corrida = 'Desafio das Montanhas');

INSERT INTO corrida (nome_corrida, data_corrida, administrador_id)
SELECT 'Maratona Cidade Azul', '10-11-2025', a.id FROM administrador a
WHERE a.nome = 'João Pereira'
  AND NOT EXISTS (SELECT 1 FROM corrida c WHERE c.nome_corrida = 'Maratona Cidade Azul');

INSERT INTO corrida (nome_corrida, data_corrida, administrador_id)
SELECT 'Corrida Solidária', '05-12-2025', a.id FROM administrador a
WHERE a.nome = 'Mariana Costa'
  AND NOT EXISTS (SELECT 1 FROM corrida c WHERE c.nome_corrida = 'Corrida Solidária');
