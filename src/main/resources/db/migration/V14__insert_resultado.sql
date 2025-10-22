
INSERT INTO resultado (tempo_prova, posicaog, posicaoc, corrida_id, atleta_id)
SELECT '00:25:32', 1, 1, c.id, a.id
FROM corrida c, atleta a
WHERE c.nome_corrida = 'Corrida da Primavera' AND a.nome = 'Pedro Lima';

INSERT INTO resultado (tempo_prova, posicaog, posicaoc, corrida_id, atleta_id)
SELECT '00:45:10', 2, 1, c.id, a.id
FROM corrida c, atleta a
WHERE c.nome_corrida = 'Desafio das Montanhas' AND a.nome = 'Ana Torres';

INSERT INTO resultado (tempo_prova, posicaog, posicaoc, corrida_id, atleta_id)
SELECT '01:20:15', 3, 1, c.id, a.id
FROM corrida c, atleta a
WHERE c.nome_corrida = 'Maratona Cidade Azul' AND a.nome = 'Rafael Santos';

INSERT INTO resultado (tempo_prova, posicaog, posicaoc, corrida_id, atleta_id)
SELECT '00:22:47', 1, 1, c.id, a.id
FROM corrida c, atleta a
WHERE c.nome_corrida = 'Corrida Solidária' AND a.nome = 'Beatriz Gomes';
