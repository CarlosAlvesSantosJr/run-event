
-- Atletas vinculados às categorias
INSERT INTO atleta (nome, data_nascimento, sexo, categoria_id, login_id)
SELECT 'Pedro Lima', '2010-05-12', 'M', c.id, l.id
FROM login l, categoria c
WHERE l.usuario = 'atleta1' AND c.nome_categoria = 'Juvenil'
    ON CONFLICT (login_id) DO NOTHING;

INSERT INTO atleta (nome, data_nascimento, sexo, categoria_id, login_id)
SELECT 'Ana Torres', '1995-09-23', 'F', c.id, l.id
FROM login l, categoria c
WHERE l.usuario = 'atleta2' AND c.nome_categoria = 'Adulto'
    ON CONFLICT (login_id) DO NOTHING;

INSERT INTO atleta (nome, data_nascimento, sexo, categoria_id, login_id)
SELECT 'Rafael Santos', '1980-01-10', 'M', c.id, l.id
FROM login l, categoria c
WHERE l.usuario = 'atleta3' AND c.nome_categoria = 'Sênior'
    ON CONFLICT (login_id) DO NOTHING;

INSERT INTO atleta (nome, data_nascimento, sexo, categoria_id, login_id)
SELECT 'Beatriz Gomes', '2008-12-02', 'F', c.id, l.id
FROM login l, categoria c
WHERE l.usuario = 'atleta4' AND c.nome_categoria = 'Juvenil'
    ON CONFLICT (login_id) DO NOTHING;
