package com.kolekta.dto.auth;

import lombok.Builder;
import lombok.Getter;

/** Identité de la personne connectée (sans jeton). */
@Getter @Builder
public class MeResponse {
    private Long userId;
    private String userType;
    private String role;
    private String nom;
    private String prenoms;
}
