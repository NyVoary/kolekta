-- ============================================================
-- KOLEKTA - Jetons révoqués (déconnexion, US-082)
-- Un jeton JWT ne peut pas être « repris » : à la déconnexion on enregistre son
-- identifiant (jti) jusqu'à sa date d'expiration, et l'API refuse (401) tout jeton listé ici.
-- Les lignes expirées sont supprimées par un nettoyage planifié (le jeton serait de toute façon refusé).
-- Clé primaire = le jti lui-même (identifiant unique du jeton), pas de BIGINT identity.
-- ============================================================

CREATE TABLE jetons_revoques (
    jti       VARCHAR(36)  PRIMARY KEY,
    expire_le TIMESTAMPTZ  NOT NULL
);

-- Le nettoyage supprime « tout ce qui a expiré » : l'index évite de parcourir toute la table
CREATE INDEX idx_jetons_revoques_expire_le ON jetons_revoques (expire_le);
