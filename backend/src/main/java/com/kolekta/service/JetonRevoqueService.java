package com.kolekta.service;

import java.time.Instant;

public interface JetonRevoqueService {

    /** Enregistre le jeton comme révoqué jusqu'à sa date d'expiration (sans effet s'il l'est déjà). */
    void revoquer(String jti, Instant expireLe);

    boolean estRevoque(String jti);

    /** Supprime les jetons révoqués déjà expirés ; retourne le nombre de lignes supprimées. */
    int purgerExpires();
}
