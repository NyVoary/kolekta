package com.kolekta.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/** Jeton JWT invalidé par une déconnexion. On le garde seulement jusqu'à sa date d'expiration. */
@Entity @Table(name = "jetons_revoques")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class JetonRevoque {
    /** Identifiant unique du jeton (claim « jti »). */
    @Id
    @Column(length = 36)
    private String jti;

    @Column(name = "expire_le", nullable = false)
    private Instant expireLe;
}
