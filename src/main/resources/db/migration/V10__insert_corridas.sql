
INSERT INTO corrida (nome_corrida, data_corrida, administrador_id)
SELECT 'Corrida da Primavera', '2025-09-15', a.id FROM administrador a WHERE a.nome = 'Carlos Silva'
UNION ALL
SELECT 'Desafio das Montanhas', '2025-10-01', a.id FROM administrador a WHERE a.nome = 'Fernanda Souza'
UNION ALL
SELECT 'Maratona Cidade Azul', '2025-11-10', a.id FROM administrador a WHERE a.nome = 'João Pereira'
UNION ALL
SELECT 'Corrida Solidária', '2025-12-05', a.id FROM administrador a WHERE a.nome = 'Mariana Costa';


--::date