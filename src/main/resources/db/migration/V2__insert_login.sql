-- inserido login de atleta

CREATE EXTENSION IF NOT EXISTS pgcrypto;

INSERT INTO login (usuario, senha, tipo_usuario)
VALUES
    ('atleta1', crypt('user123', gen_salt('bf')), 'ATLETA'),
    ('atleta2', crypt('user456', gen_salt('bf')), 'ATLETA'),
    ('atleta3', crypt('user789', gen_salt('bf')), 'ATLETA'),
    ('atleta4', crypt('user000', gen_salt('bf')), 'ATLETA')
    ON CONFLICT (usuario) DO NOTHING;

-- inserido login de administrador
INSERT INTO login (usuario, senha, tipo_usuario)
VALUES
    ('admin_carlos', crypt('admin123', gen_salt('bf')), 'ADMINISTRADOR'),
    ('admin_fernanda', crypt('admin456', gen_salt('bf')), 'ADMINISTRADOR'),
    ('admin_joao', crypt('admin789', gen_salt('bf')), 'ADMINISTRADOR'),
    ('admin_mariana', crypt('admin000', gen_salt('bf')), 'ADMINISTRADOR')
    ON CONFLICT (usuario) DO NOTHING;