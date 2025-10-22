
INSERT INTO categoria (nome_categoria, faixa_etaria)
VALUES
    ('Infantil', '6-11'),
    ('Juvenil', '12-17'),
    ('Adulto', '18-39'),
    ('Sênior', '40+')
    ON CONFLICT (nome_categoria) DO NOTHING;
