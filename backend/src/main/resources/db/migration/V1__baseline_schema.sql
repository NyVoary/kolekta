-- ============================================================
-- KOLEKTA - Schéma complet de la base de données
-- ============================================================



-- ============================================================
-- SECTION 1 : TABLES DE RÉFÉRENCE
-- ============================================================

CREATE TABLE TypeAssignation (
    id_type  SERIAL PRIMARY KEY,
    libelle  VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE ModePaiement (
    id_mode  SERIAL PRIMARY KEY,
    libelle  VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE Sexe (
    code     CHAR(1) PRIMARY KEY,
    libelle  VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE Roles (
    id_role  SERIAL PRIMARY KEY,
    libelle  VARCHAR(20) UNIQUE NOT NULL
);

-- Statuts du cycle de vie d'une commande
CREATE TABLE Status (
    id_status  SERIAL PRIMARY KEY,
    libelle    VARCHAR(100) NOT NULL
);

-- Statuts du cycle de vie d'un paiement commande
CREATE TABLE StatusPaiement (
    id_status_paiement  SERIAL PRIMARY KEY,
    libelle             VARCHAR(100) NOT NULL
);

CREATE TABLE StatusTaches (
    id_status  SERIAL PRIMARY KEY,
    libelle    VARCHAR(100) NOT NULL
);

CREATE TABLE PrioritesTaches (
    id_priorite  SERIAL PRIMARY KEY,
    libelle      VARCHAR(100) NOT NULL
);


-- ============================================================
-- SECTION 2 : EMPLOYÉS
-- ============================================================

CREATE TABLE Employes (
    id_employe        SERIAL PRIMARY KEY,
    nom               VARCHAR(100) NOT NULL,
    prenoms           VARCHAR(100) NOT NULL,
    dtn               DATE NOT NULL,
    sexe              CHAR(1),
    email             VARCHAR(255),
    telephone         VARCHAR(20),
    adresse           VARCHAR(255),
    poste             VARCHAR(100),
    date_embauche     DATE NOT NULL,
    date_fin_embauche DATE NOT NULL,
    salaire           NUMERIC(10, 2) NOT NULL,
    id_mode           INT NOT NULL,
    image             VARCHAR(100),
    FOREIGN KEY (sexe)    REFERENCES Sexe(code),
    FOREIGN KEY (id_mode) REFERENCES ModePaiement(id_mode)
);

-- Séparé de Employes : un profil RH ≠ des credentials d'accès
CREATE TABLE Acces (
    id_acces        SERIAL PRIMARY KEY,
    id_employe      INT NOT NULL,
    nom_utilisateur VARCHAR(50) UNIQUE NOT NULL,
    mot_de_passe    VARCHAR(255) NOT NULL,
    id_role         INT NOT NULL,
    FOREIGN KEY (id_employe) REFERENCES Employes(id_employe) ON DELETE CASCADE,
    FOREIGN KEY (id_role)    REFERENCES Roles(id_role)
);

CREATE TABLE Paiement_Employe (
    id_paiement_employe SERIAL PRIMARY KEY,
    id_employe          INT NOT NULL,
    mois                INT NOT NULL CHECK (mois BETWEEN 1 AND 12),
    annee               INT NOT NULL,
    date_paiement       DATE NOT NULL,
    montant             NUMERIC(10, 2) NOT NULL,
    UNIQUE (id_employe, mois, annee),
    FOREIGN KEY (id_employe) REFERENCES Employes(id_employe) ON DELETE CASCADE
);

CREATE TABLE Historique_Employe (
    id_historique SERIAL PRIMARY KEY,
    id_employe    INT NOT NULL,
    date_action   TIMESTAMP NOT NULL DEFAULT NOW(),
    action        VARCHAR(255) NOT NULL,
    cible_type    VARCHAR(100),
    id_cible      INT,
    commentaire   TEXT,
    FOREIGN KEY (id_employe) REFERENCES Employes(id_employe) ON DELETE CASCADE
);


-- ============================================================
-- SECTION 3 : CLIENTS
-- ============================================================

CREATE TABLE Clients (
    id_client          SERIAL PRIMARY KEY,
    nom                VARCHAR(100) NOT NULL,
    prenoms            VARCHAR(100) NOT NULL,
    dtn                DATE NOT NULL,
    sexe               CHAR(1),
    email              VARCHAR(255) UNIQUE,
    telephone          VARCHAR(20) NOT NULL,
    adresse            VARCHAR(255) NOT NULL,
    mdp                VARCHAR(255),
    image              VARCHAR(100),
    date_creation      DATE NOT NULL DEFAULT CURRENT_DATE,
    estActif           BOOLEAN NOT NULL DEFAULT TRUE,
    date_desactivation DATE DEFAULT NULL,
    FOREIGN KEY (sexe) REFERENCES Sexe(code)
);


-- ============================================================
-- SECTION 4 : FOURNISSEURS
-- ============================================================

CREATE TABLE Fournisseurs (
    id_fournisseur     SERIAL PRIMARY KEY,
    nom                VARCHAR(100) NOT NULL,
    adresse            VARCHAR(255) NOT NULL,
    telephone          VARCHAR(20),
    email              VARCHAR(255),
    image              VARCHAR(100),
    date_creation      DATE NOT NULL DEFAULT CURRENT_DATE,
    estActif           BOOLEAN NOT NULL DEFAULT TRUE,
    date_desactivation DATE DEFAULT NULL
);

CREATE TABLE Paiement_Fournisseur (
    id_paiement_fournisseur SERIAL PRIMARY KEY,
    id_employe              INT,
    id_fournisseur          INT NOT NULL,
    montant                 NUMERIC(10, 2) NOT NULL,
    id_mode                 INT NOT NULL,
    date_paiement           DATE NOT NULL,
    FOREIGN KEY (id_employe)     REFERENCES Employes(id_employe) ON DELETE SET NULL,
    FOREIGN KEY (id_fournisseur) REFERENCES Fournisseurs(id_fournisseur) ON DELETE CASCADE,
    FOREIGN KEY (id_mode)        REFERENCES ModePaiement(id_mode)
);


-- ============================================================
-- SECTION 5 : PRODUITS ET CATÉGORIES
-- ============================================================

CREATE TABLE Categories (
    id_categorie SERIAL PRIMARY KEY,
    libelle      VARCHAR(100) NOT NULL
);

-- id_fournisseur nullable : un produit peut exister sans fournisseur principal
-- (la relation multi-fournisseur est gérée dans Disponibilites_Fournisseur)
CREATE TABLE Produits (
    id_produit     SERIAL PRIMARY KEY,
    id_fournisseur INT,
    nom            VARCHAR(100) NOT NULL,
    description    TEXT,
    id_categorie   INT NOT NULL,
    unite_mesure   VARCHAR(100) NOT NULL,
    prix_achat     NUMERIC(10, 2) NOT NULL,
    prix_vente     NUMERIC(10, 2) NOT NULL,
    date_ajout     DATE NOT NULL DEFAULT CURRENT_DATE,
    image          VARCHAR(100),
    FOREIGN KEY (id_fournisseur) REFERENCES Fournisseurs(id_fournisseur) ON DELETE SET NULL,
    FOREIGN KEY (id_categorie)   REFERENCES Categories(id_categorie) ON DELETE RESTRICT
);

CREATE TABLE Disponibilites_Fournisseur (
    id_dispo            SERIAL PRIMARY KEY,
    id_fournisseur      INT NOT NULL,
    id_produit          INT NOT NULL,
    quantite_disponible INT NOT NULL DEFAULT 0,
    id_employe          INT,
    UNIQUE (id_fournisseur, id_produit),
    FOREIGN KEY (id_fournisseur) REFERENCES Fournisseurs(id_fournisseur) ON DELETE CASCADE,
    FOREIGN KEY (id_produit)     REFERENCES Produits(id_produit) ON DELETE CASCADE,
    FOREIGN KEY (id_employe)     REFERENCES Employes(id_employe) ON DELETE SET NULL
);

-- Saisonnalité : mois de disponibilité naturelle d'un produit
CREATE TABLE Produit_mois (
    id_produit_mois SERIAL PRIMARY KEY,
    id_produit      INT NOT NULL,
    mois            INT NOT NULL CHECK (mois BETWEEN 1 AND 12),
    UNIQUE (id_produit, mois),
    FOREIGN KEY (id_produit) REFERENCES Produits(id_produit) ON DELETE CASCADE
);


-- ============================================================
-- SECTION 6 : COMMANDES ET PAIEMENTS
-- ============================================================

CREATE TABLE Panier (
    id_panier     SERIAL PRIMARY KEY,
    id_client     INT NOT NULL,
    date_creation TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY (id_client) REFERENCES Clients(id_client) ON DELETE CASCADE
);

CREATE TABLE Panier_Produits (
    id_panier_produit SERIAL PRIMARY KEY,
    id_panier         INT NOT NULL,
    id_produit        INT NOT NULL,
    quantite          INT NOT NULL CHECK (quantite > 0),
    prix_unitaire     NUMERIC(10, 2) NOT NULL,
    UNIQUE (id_panier, id_produit),
    FOREIGN KEY (id_panier)  REFERENCES Panier(id_panier) ON DELETE CASCADE,
    FOREIGN KEY (id_produit) REFERENCES Produits(id_produit) ON DELETE CASCADE
);

-- Les champs adresse/telephone/email sont des snapshots au moment de la commande
-- (le client peut changer ses infos après, la commande garde l'adresse d'origine)
CREATE TABLE Commandes (
    id_commande         SERIAL PRIMARY KEY,
    id_client           INT NOT NULL,
    date_commande       DATE NOT NULL DEFAULT CURRENT_DATE,
    frais_livraison     NUMERIC(10, 2) NOT NULL DEFAULT 0,
    montant_total       NUMERIC(10, 2) NOT NULL,
    adresse_livraison   VARCHAR(255) NOT NULL,
    ville_livraison     VARCHAR(100) NOT NULL,
    telephone_livraison VARCHAR(20) NOT NULL,
    email_livraison     VARCHAR(255),
    FOREIGN KEY (id_client) REFERENCES Clients(id_client) ON DELETE RESTRICT
);

CREATE TABLE Commande_Produits (
    id_commande_produit SERIAL PRIMARY KEY,
    id_commande         INT NOT NULL,
    id_produit          INT NOT NULL,
    quantite            INT NOT NULL CHECK (quantite > 0),
    prix_unitaire       NUMERIC(10, 2) NOT NULL,
    FOREIGN KEY (id_commande) REFERENCES Commandes(id_commande) ON DELETE CASCADE,
    FOREIGN KEY (id_produit)  REFERENCES Produits(id_produit) ON DELETE RESTRICT
);

-- Historique complet des changements de statut d'une commande
CREATE TABLE Commande_Status (
    id_commande_status  SERIAL PRIMARY KEY,
    id_commande         INT NOT NULL,
    id_status           INT NOT NULL,
    date_changement     TIMESTAMP NOT NULL DEFAULT NOW(),
    id_employe          INT,
    commentaire_employe TEXT,
    FOREIGN KEY (id_employe)  REFERENCES Employes(id_employe) ON DELETE SET NULL,
    FOREIGN KEY (id_commande) REFERENCES Commandes(id_commande) ON DELETE CASCADE,
    FOREIGN KEY (id_status)   REFERENCES Status(id_status)
);

CREATE TABLE Commande_Paiement (
    id_commande_paiement SERIAL PRIMARY KEY,
    id_commande          INT NOT NULL,
    date_paiement        DATE NOT NULL,
    montant              NUMERIC(10, 2) NOT NULL,
    id_mode              INT NOT NULL,
    FOREIGN KEY (id_commande) REFERENCES Commandes(id_commande) ON DELETE CASCADE,
    FOREIGN KEY (id_mode)     REFERENCES ModePaiement(id_mode)
);

-- Historique des changements de statut d'un paiement
CREATE TABLE Paiement_Status (
    id_paiement_status   SERIAL PRIMARY KEY,
    id_commande_paiement INT NOT NULL,
    id_status_paiement   INT NOT NULL,
    date_changement      TIMESTAMP NOT NULL DEFAULT NOW(),
    id_employe           INT,
    commentaire_employe  TEXT,
    FOREIGN KEY (id_employe)             REFERENCES Employes(id_employe) ON DELETE SET NULL,
    FOREIGN KEY (id_commande_paiement)   REFERENCES Commande_Paiement(id_commande_paiement) ON DELETE CASCADE,
    FOREIGN KEY (id_status_paiement)     REFERENCES StatusPaiement(id_status_paiement)
);


-- ============================================================
-- SECTION 7 : CAMIONS ET LIVRAISONS
-- ============================================================

-- chauffeur_nom / chauffeur_telephone : chauffeurs externes (sous-traitants)
-- Les employés internes affectés aux tournées sont dans Assignations_camions.id_employe
CREATE TABLE Camions (
    id_camion           SERIAL PRIMARY KEY,
    immatriculation     VARCHAR(20) NOT NULL UNIQUE,
    capacite            NUMERIC(10, 2) NOT NULL,
    chauffeur_nom       VARCHAR(100),
    chauffeur_telephone VARCHAR(20)
);

CREATE TABLE Assignations_camions (
    id_assignation_camion SERIAL PRIMARY KEY,
    id_employe            INT,
    id_camion             INT NOT NULL,
    depart                TIMESTAMP NOT NULL,
    arrivee               TIMESTAMP,
    id_type               INT NOT NULL,
    FOREIGN KEY (id_employe) REFERENCES Employes(id_employe) ON DELETE SET NULL,
    FOREIGN KEY (id_camion)  REFERENCES Camions(id_camion) ON DELETE CASCADE,
    FOREIGN KEY (id_type)    REFERENCES TypeAssignation(id_type)
);

CREATE TABLE Commandes_Camions (
    id_commande_camion    SERIAL PRIMARY KEY,
    id_employe            INT,
    id_assignation_camion INT NOT NULL,
    id_commande           INT NOT NULL,
    date_assignation      TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY (id_employe)            REFERENCES Employes(id_employe) ON DELETE SET NULL,
    FOREIGN KEY (id_commande)           REFERENCES Commandes(id_commande) ON DELETE CASCADE,
    FOREIGN KEY (id_assignation_camion) REFERENCES Assignations_camions(id_assignation_camion) ON DELETE CASCADE
);

CREATE TABLE Paiement_Camions (
    id_paiement_camion    SERIAL PRIMARY KEY,
    id_employe            INT,
    id_assignation_camion INT NOT NULL,
    montant               NUMERIC(10, 2) NOT NULL,
    date_paiement         TIMESTAMP NOT NULL,
    id_mode               INT NOT NULL,
    FOREIGN KEY (id_employe)            REFERENCES Employes(id_employe) ON DELETE SET NULL,
    FOREIGN KEY (id_assignation_camion) REFERENCES Assignations_camions(id_assignation_camion) ON DELETE CASCADE,
    FOREIGN KEY (id_mode)               REFERENCES ModePaiement(id_mode)
);

CREATE TABLE Livraisons (
    id_livraison          SERIAL PRIMARY KEY,
    id_commande           INT NOT NULL,
    id_assignation_camion INT,
    date_livraison        DATE NOT NULL,
    FOREIGN KEY (id_commande)           REFERENCES Commandes(id_commande) ON DELETE CASCADE,
    FOREIGN KEY (id_assignation_camion) REFERENCES Assignations_camions(id_assignation_camion) ON DELETE SET NULL
);


-- ============================================================
-- SECTION 8 : GESTION DES TÂCHES
-- ============================================================

CREATE TABLE AssignationsTaches (
    id_tache       SERIAL PRIMARY KEY,
    titre          VARCHAR(255) NOT NULL,
    description    TEXT,
    id_employe     INT NOT NULL,
    id_priorite    INT NOT NULL,
    statusActuelle INT NOT NULL,
    date_debut     TIMESTAMP NOT NULL,
    date_fin       TIMESTAMP NOT NULL,
    FOREIGN KEY (id_employe)     REFERENCES Employes(id_employe) ON DELETE CASCADE,
    FOREIGN KEY (id_priorite)    REFERENCES PrioritesTaches(id_priorite) ON DELETE RESTRICT,
    FOREIGN KEY (statusActuelle) REFERENCES StatusTaches(id_status) ON DELETE RESTRICT
);

CREATE TABLE HistoriqueStatusTaches (
    id               SERIAL PRIMARY KEY,
    id_tache         INT NOT NULL,
    modifie_par      INT NOT NULL,
    id_status_taches INT NOT NULL,
    date             TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY (id_tache)          REFERENCES AssignationsTaches(id_tache) ON DELETE CASCADE,
    FOREIGN KEY (modifie_par)       REFERENCES Employes(id_employe) ON DELETE CASCADE,
    FOREIGN KEY (id_status_taches)  REFERENCES StatusTaches(id_status) ON DELETE CASCADE
);


-- ============================================================
-- SECTION 9 : PARAMÈTRES SYSTÈME
-- ============================================================

-- type : 'text' | 'int' | 'decimal' | 'boolean'
CREATE TABLE Parametre (
    id_parametre SERIAL PRIMARY KEY,
    libelle      VARCHAR(100) NOT NULL UNIQUE,
    valeur       TEXT,
    type         VARCHAR(20) NOT NULL DEFAULT 'text'
);


-- ============================================================
-- SECTION 10 : INDEX
-- ============================================================

CREATE INDEX idx_acces_employe          ON Acces(id_employe);
CREATE INDEX idx_commandes_client       ON Commandes(id_client);
CREATE INDEX idx_commande_produits      ON Commande_Produits(id_commande);
CREATE INDEX idx_commande_status        ON Commande_Status(id_commande);
CREATE INDEX idx_commande_paiement      ON Commande_Paiement(id_commande);
CREATE INDEX idx_paiement_status        ON Paiement_Status(id_commande_paiement);
CREATE INDEX idx_panier_client          ON Panier(id_client);
CREATE INDEX idx_panier_produits        ON Panier_Produits(id_panier);
CREATE INDEX idx_taches_employe         ON AssignationsTaches(id_employe);
CREATE INDEX idx_assignations_camion    ON Assignations_camions(id_camion);
CREATE INDEX idx_commandes_camions      ON Commandes_Camions(id_assignation_camion);
CREATE INDEX idx_disponibilites_produit ON Disponibilites_Fournisseur(id_produit);


-- ============================================================
-- SECTION 11 : VUES
-- ============================================================

CREATE OR REPLACE VIEW v_camion_etat AS
SELECT
    COALESCE((
        SELECT a.id_assignation_camion
        FROM   Assignations_camions a
        WHERE  a.id_camion = c.id_camion AND a.arrivee IS NULL
        LIMIT  1
    ), 0) AS id_assignation_camion,
    c.*,
    COALESCE((
        SELECT ta.libelle
        FROM   Assignations_camions a
        JOIN   TypeAssignation ta ON ta.id_type = a.id_type
        WHERE  a.id_camion = c.id_camion AND a.arrivee IS NULL
        LIMIT  1
    ), 'Disponible') AS etat_camion
FROM Camions AS c;
