package com.kolekta.service;

import com.kolekta.dto.auth.LoginRequest;
import com.kolekta.dto.auth.LoginResponse;
import com.kolekta.dto.auth.MeResponse;

import java.time.Instant;

public interface AuthService {

    /** Connexion unique : le login est un nom d'utilisateur (employé) ou un e-mail (client). */
    LoginResponse login(LoginRequest request);

    /** Identité de la personne connectée ; refuse un compte devenu inactif depuis la connexion. */
    MeResponse me(Long userId, String userType);

    /** Déconnexion : le jeton ne sera plus accepté, même avant son expiration. */
    void logout(String jti, Instant expireLe);
}
