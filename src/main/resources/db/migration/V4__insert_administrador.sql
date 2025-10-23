INSERT INTO administrador (nome, login_id)
SELECT 'Carlos Silva', id FROM login WHERE usuario = 'admin_carlos'
    ON CONFLICT (login_id) DO NOTHING;

INSERT INTO administrador (nome, login_id)
SELECT 'Fernanda Souza', id FROM login WHERE usuario = 'admin_fernanda'
    ON CONFLICT (login_id) DO NOTHING;

INSERT INTO administrador (nome, login_id)
SELECT 'João Pereira', id FROM login WHERE usuario = 'admin_joao'
    ON CONFLICT (login_id) DO NOTHING;

INSERT INTO administrador (nome, login_id)
SELECT 'Mariana Costa', id FROM login WHERE usuario = 'admin_mariana'
    ON CONFLICT (login_id) DO NOTHING;