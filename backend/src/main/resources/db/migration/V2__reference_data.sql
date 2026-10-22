-- ============================================================
-- KOLEKTA - Données de référence indispensables
-- Aucune donnée personnelle ici.
-- ============================================================

-- Rôles du cahier des charges (SecurityConfig : ROLE_ + libellé en majuscules)
INSERT INTO roles (libelle) VALUES
('admin'),
('gerant'),
('rh'),
('collecteur');

INSERT INTO sexe (code, libelle) VALUES
('M', 'Masculin'),
('F', 'Feminin'),
('A', 'Autre');

-- Obligatoire pour créer un employé (employes.id_mode)
INSERT INTO modepaiement (libelle) VALUES
('Virement'),
('Chèque'),
('Espèces'),
('Carte bancaire'),
('Mobile Money');
