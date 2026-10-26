package com.kolekta.controller;

import com.kolekta.dto.auth.LoginRequest;
import com.kolekta.dto.auth.LoginResponse;
import com.kolekta.dto.auth.MeResponse;
import com.kolekta.security.JwtUtil;
import com.kolekta.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    /** Public. Le login est un nom d'utilisateur (employé) ou un e-mail (client). */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    /** Identité de la personne connectée (utilisé pour restaurer la session au rechargement de la page). */
    @GetMapping("/me")
    public ResponseEntity<MeResponse> me(Authentication authentication) {
        // JwtAuthFilter place l'id dans le principal et le type de compte dans les détails
        Long userId = (Long) authentication.getPrincipal();
        String userType = (String) authentication.getDetails();
        return ResponseEntity.ok(authService.me(userId, userType));
    }

    /** Révoque le jeton utilisé pour cet appel : il sera refusé (401) jusqu'à son expiration. */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        String token = authorization.substring("Bearer ".length());
        authService.logout(jwtUtil.getJti(token), jwtUtil.getExpiration(token));
        return ResponseEntity.noContent().build();
    }
}
