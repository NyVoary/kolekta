-- ============================================================
-- KOLEKTA - Données de référence indispensables
-- Ce que le code attend en base pour fonctionner (rôles, statuts, modes
-- de paiement, priorités...). Aucune donnée personnelle ici.
-- ============================================================

INSERT INTO TypeAssignation (libelle) VALUES
('Collecte'),
('Livraison');

INSERT INTO ModePaiement (libelle) VALUES
('Virement'),
('Chèque'),
('Espèces'),
('Carte bancaire'),
('Mobile Money');

INSERT INTO Sexe (code, libelle) VALUES
('M', 'Masculin'),
('F', 'Feminin'),
('A', 'Autre');

-- Les rôles employé utilisés par SecurityConfig (ROLE_ + libellé en majuscules)
INSERT INTO Roles (libelle) VALUES
('admin'),
('gerant'),
('rh'),
('livreur'),
('employe');

-- Statuts d'une commande. 'En attente' : statut initial attendu par CommandeServiceImpl.
INSERT INTO Status (libelle) VALUES
('En attente'),
('Validée'),
('En cours de traitement'),
('Expédiée'),
('En livraison'),
('Livrée'),
('Annulée');

INSERT INTO StatusPaiement (libelle) VALUES
('En attente'),
('Validé'),
('Annulé');

INSERT INTO StatusTaches (libelle) VALUES
('En attente'),
('En cours'),
('Terminée'),
('Annulée');

INSERT INTO PrioritesTaches (libelle) VALUES
('Basse'),
('Moyenne'),
('Haute'),
('Urgente');

INSERT INTO Categories (libelle) VALUES
('Fruit'),
('Légume');
