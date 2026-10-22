-- ============================================================
-- KOLEKTA - Données de DÉMONSTRATION (profil Spring "dev" uniquement)
-- Entièrement fictives : aucune vraie personne, e-mails en @example.com,
-- numéros de téléphone inventés. Mots de passe hachés en BCrypt.
-- Les identifiants de connexion sont documentés dans le README.
-- ============================================================

-- Employés (date_fin_embauche NULL = en poste)
INSERT INTO employes (nom, prenoms, dtn, sexe, email, telephone, adresse, poste, date_embauche, salaire, id_mode) VALUES
('DEMO', 'Admin',       '1980-01-01', 'A', 'admin.demo@example.com',       '0340000001', '1 rue de la Démo, Ville Fictive', 'Administrateur',     '2024-01-01', 1000000.00, (SELECT id_mode FROM modepaiement WHERE libelle = 'Virement')),
('DEMO', 'Gérant',      '1981-02-02', 'A', 'gerant.demo@example.com',      '0340000002', '2 rue de la Démo, Ville Fictive', 'Gérant',             '2024-01-01',  900000.00, (SELECT id_mode FROM modepaiement WHERE libelle = 'Virement')),
('DEMO', 'RH',          '1982-03-03', 'A', 'rh.demo@example.com',          '0340000003', '3 rue de la Démo, Ville Fictive', 'Responsable RH',     '2024-01-01',  800000.00, (SELECT id_mode FROM modepaiement WHERE libelle = 'Espèces')),
('DEMO', 'Collecteur',  '1983-04-04', 'A', 'collecteur.demo@example.com',  '0340000004', '4 rue de la Démo, Ville Fictive', 'Collecteur terrain', '2024-01-01',  600000.00, (SELECT id_mode FROM modepaiement WHERE libelle = 'Espèces'));

INSERT INTO acces (id_employe, nom_utilisateur, mot_de_passe, id_role)
SELECT e.id_employe, v.login, v.hash, (SELECT id_role FROM roles WHERE libelle = v.role)
FROM (VALUES
    ('admin.demo@example.com',      'admin.demo',      '$2b$10$mztMDYOocYly7tjpCvg6l.N9SEzEwvcS.sfpqZY9.kyP3Xr68M.r6',      'admin'),
    ('gerant.demo@example.com',     'gerant.demo',     '$2b$10$K8EJBMr5prJ/FgPTzu1oI..vn5dgYyLeQPpE2keMdCp.zC2r9rQ9e',     'gerant'),
    ('rh.demo@example.com',         'rh.demo',         '$2b$10$TpVFXovT4z.BhPRl/P6O1OKP9dvoPxaBhj2963z7Iftvpgy2/DJ6K',         'rh'),
    ('collecteur.demo@example.com', 'collecteur.demo', '$2b$10$Rx8toaZaHW/TuHMEolIxbu04c.7TBprR9i/1aorwDyKBcFb/lB22S', 'collecteur')
) AS v(email, login, hash, role)
JOIN employes e ON e.email = v.email;

-- Clients : un particulier et un client B2B (le B2B ne se distingue que par son nom
-- pour l'instant ; un vrai type de client viendra avec US-002)
INSERT INTO clients (nom, prenoms, dtn, sexe, email, telephone, adresse, mdp) VALUES
('EXEMPLE', 'Alice',         '1990-05-05', 'A', 'alice.demo@example.com',    '0340000011', '10 avenue de l''Exemple, Ville Fictive', '$2b$10$nbXQfV02TH99sa4XNujO0e6hw0TcNAjNC3xXMZc6HcLS68qYr.5jK'),
('SOCIETE EXEMPLE SARL', 'Contact B2B', '1985-06-06', 'A', 'contact.b2b@example.com', '0340000012', '20 zone d''activité Exemple, Ville Fictive', '$2b$10$nbXQfV02TH99sa4XNujO0e6hw0TcNAjNC3xXMZc6HcLS68qYr.5jK');
