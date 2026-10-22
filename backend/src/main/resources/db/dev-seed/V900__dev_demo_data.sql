-- ============================================================
-- KOLEKTA - Données de DÉMONSTRATION (profil Spring "dev" uniquement)
-- Entièrement fictives : aucune vraie personne, e-mails en @example.com,
-- numéros de téléphone inventés. Mots de passe hachés en BCrypt.
-- Les identifiants de connexion sont documentés dans le README.
-- ============================================================

-- Employés (date_fin_embauche est NOT NULL dans le schéma : date lointaine)
INSERT INTO Employes (nom, prenoms, dtn, sexe, email, telephone, adresse, poste, date_embauche, date_fin_embauche, salaire, id_mode) VALUES
('DEMO', 'Admin',   '1980-01-01', 'A', 'admin.demo@example.com',   '0340000001', '1 rue de la Démo, Ville Fictive', 'Administrateur',    '2024-01-01', '2099-12-31', 1000000.00, 1),
('DEMO', 'Gérant',  '1981-02-02', 'A', 'gerant.demo@example.com',  '0340000002', '2 rue de la Démo, Ville Fictive', 'Gérant',            '2024-01-01', '2099-12-31',  900000.00, 1),
('DEMO', 'RH',      '1982-03-03', 'A', 'rh.demo@example.com',      '0340000003', '3 rue de la Démo, Ville Fictive', 'Responsable RH',    '2024-01-01', '2099-12-31',  800000.00, 3),
('DEMO', 'Livreur', '1983-04-04', 'A', 'livreur.demo@example.com', '0340000004', '4 rue de la Démo, Ville Fictive', 'Chauffeur-livreur', '2024-01-01', '2099-12-31',  600000.00, 3);

INSERT INTO Acces (id_employe, nom_utilisateur, mot_de_passe, id_role) VALUES
(1, 'admin.demo',   '$2b$10$mztMDYOocYly7tjpCvg6l.N9SEzEwvcS.sfpqZY9.kyP3Xr68M.r6',   (SELECT id_role FROM Roles WHERE libelle = 'admin')),
(2, 'gerant.demo',  '$2b$10$K8EJBMr5prJ/FgPTzu1oI..vn5dgYyLeQPpE2keMdCp.zC2r9rQ9e',  (SELECT id_role FROM Roles WHERE libelle = 'gerant')),
(3, 'rh.demo',      '$2b$10$TpVFXovT4z.BhPRl/P6O1OKP9dvoPxaBhj2963z7Iftvpgy2/DJ6K',      (SELECT id_role FROM Roles WHERE libelle = 'rh')),
(4, 'livreur.demo', '$2b$10$pJzxalqSpb/.0JYrqa2Eoe6.IyON5E4beiwI68veSVZd5pVCHdjye', (SELECT id_role FROM Roles WHERE libelle = 'livreur'));

-- Clients
INSERT INTO Clients (nom, prenoms, dtn, sexe, email, telephone, adresse, mdp) VALUES
('EXEMPLE', 'Alice', '1990-05-05', 'A', 'alice.demo@example.com', '0340000011', '10 avenue de l''Exemple, Ville Fictive', '$2b$10$nbXQfV02TH99sa4XNujO0e6hw0TcNAjNC3xXMZc6HcLS68qYr.5jK'),
('EXEMPLE', 'Bob',   '1991-06-06', 'A', 'bob.demo@example.com',   '0340000012', '11 avenue de l''Exemple, Ville Fictive', '$2b$10$nbXQfV02TH99sa4XNujO0e6hw0TcNAjNC3xXMZc6HcLS68qYr.5jK');

-- Fournisseurs
INSERT INTO Fournisseurs (nom, adresse, telephone, email) VALUES
('Ferme Démo Un',    'Route de la Colline, Ville Fictive', '0340000021', 'contact.un@example.com'),
('Ferme Démo Deux',  'Chemin du Verger, Ville Fictive',    '0340000022', 'contact.deux@example.com'),
('Ferme Démo Trois', 'Allée des Champs, Ville Fictive',    '0340000023', 'contact.trois@example.com');

-- Produits
INSERT INTO Produits (id_fournisseur, nom, description, id_categorie, unite_mesure, prix_achat, prix_vente) VALUES
(1, 'Carotte',        'Carottes fraîches',         (SELECT id_categorie FROM Categories WHERE libelle = 'Légume'), 'kg',         800.00, 1200.00),
(2, 'Pomme de terre', 'Pommes de terre de saison', (SELECT id_categorie FROM Categories WHERE libelle = 'Légume'), 'kg',        1000.00, 1500.00),
(3, 'Chou vert',      'Choux verts',               (SELECT id_categorie FROM Categories WHERE libelle = 'Légume'), 'pièce',      400.00,  700.00),
(1, 'Haricot vert',   'Haricots verts croquants',  (SELECT id_categorie FROM Categories WHERE libelle = 'Légume'), 'kg',        1200.00, 1800.00),
(2, 'Oignon',         'Oignons secs',              (SELECT id_categorie FROM Categories WHERE libelle = 'Légume'), 'kg',         900.00, 1300.00),
(3, 'Pomme',          'Pommes locales',            (SELECT id_categorie FROM Categories WHERE libelle = 'Fruit'),  'kg',        1500.00, 2200.00),
(1, 'Poire',          'Poires douces et juteuses', (SELECT id_categorie FROM Categories WHERE libelle = 'Fruit'),  'kg',        1600.00, 2300.00),
(2, 'Fraise',         'Fraises de saison',         (SELECT id_categorie FROM Categories WHERE libelle = 'Fruit'),  'barquette', 2000.00, 3000.00);

INSERT INTO Disponibilites_Fournisseur (id_fournisseur, id_produit, quantite_disponible, id_employe) VALUES
(1, 1, 200, 1), (2, 2, 300, 1), (3, 3, 150, 1), (1, 4, 120, 1),
(2, 5, 250, 1), (3, 6, 180, 1), (1, 7, 100, 1), (2, 8,  60, 1);

-- Camions (plaques et chauffeurs fictifs)
INSERT INTO Camions (immatriculation, capacite, chauffeur_nom, chauffeur_telephone) VALUES
('0001 DEM', 3000.00, 'Chauffeur Démo A', '0340000031'),
('0002 DEM', 2000.00, 'Chauffeur Démo B', '0340000032');

-- Tâches
INSERT INTO AssignationsTaches (titre, description, id_employe, id_priorite, statusActuelle, date_debut, date_fin) VALUES
('Réunion d''équipe',    'Point hebdomadaire de démonstration', 2, (SELECT id_priorite FROM PrioritesTaches WHERE libelle = 'Haute'),   (SELECT id_status FROM StatusTaches WHERE libelle = 'En cours'),   CURRENT_DATE + INTERVAL '9 hours',  CURRENT_DATE + INTERVAL '10 hours'),
('Contrôle des stocks',  'Vérifier les quantités disponibles',  3, (SELECT id_priorite FROM PrioritesTaches WHERE libelle = 'Moyenne'), (SELECT id_status FROM StatusTaches WHERE libelle = 'En attente'), CURRENT_DATE + INTERVAL '11 hours', CURRENT_DATE + INTERVAL '13 hours'),
('Tournée de livraison', 'Livrer les commandes du jour',        4, (SELECT id_priorite FROM PrioritesTaches WHERE libelle = 'Urgente'), (SELECT id_status FROM StatusTaches WHERE libelle = 'En attente'), CURRENT_DATE + INTERVAL '14 hours', CURRENT_DATE + INTERVAL '17 hours');

-- Une commande d'exemple pour Alice, avec son statut et son paiement
INSERT INTO Commandes (id_client, frais_livraison, montant_total, adresse_livraison, ville_livraison, telephone_livraison, email_livraison) VALUES
(1, 2000.00, 6400.00, '10 avenue de l''Exemple', 'Ville Fictive', '0340000011', 'alice.demo@example.com');

INSERT INTO Commande_Produits (id_commande, id_produit, quantite, prix_unitaire) VALUES
(1, 1, 2, 1200.00),
(1, 3, 2,  700.00);

INSERT INTO Commande_Status (id_commande, id_status, id_employe) VALUES
(1, (SELECT id_status FROM Status WHERE libelle = 'En attente'), NULL);

INSERT INTO Commande_Paiement (id_commande, date_paiement, montant, id_mode) VALUES
(1, CURRENT_DATE, 6400.00, (SELECT id_mode FROM ModePaiement WHERE libelle = 'Espèces'));
