
INSERT INTO corrida_categoria (corrida_id, categoria_id)
SELECT c.id, cat.id FROM corrida c, categoria cat
WHERE c.nome_corrida = 'Corrida da Primavera' AND cat.nome_categoria IN ('Juvenil', 'Adulto')
    ON CONFLICT DO NOTHING;

INSERT INTO corrida_categoria (corrida_id, categoria_id)
SELECT c.id, cat.id FROM corrida c, categoria cat
WHERE c.nome_corrida = 'Desafio das Montanhas' AND cat.nome_categoria IN ('Adulto', 'Sênior')
    ON CONFLICT DO NOTHING;

INSERT INTO corrida_categoria (corrida_id, categoria_id)
SELECT c.id, cat.id FROM corrida c, categoria cat
WHERE c.nome_corrida = 'Maratona Cidade Azul' AND cat.nome_categoria IN ('Adulto')
    ON CONFLICT DO NOTHING;

INSERT INTO corrida_categoria (corrida_id, categoria_id)
SELECT c.id, cat.id FROM corrida c, categoria cat
WHERE c.nome_corrida = 'Corrida Solidária' AND cat.nome_categoria IN ('Infantil', 'Juvenil')
    ON CONFLICT DO NOTHING;
