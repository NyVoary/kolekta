package com.kolekta.service.impl;

import com.kolekta.dto.auth.LoginRequest;
import com.kolekta.dto.auth.LoginResponse;
import com.kolekta.entity.Acces;
import com.kolekta.entity.Clients;
import com.kolekta.exception.UnauthorizedException;
import com.kolekta.repository.AccesRepository;
import com.kolekta.repository.ClientsRepository;
import com.kolekta.security.JwtUtil;
import com.kolekta.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccesRepository accesRepository;
    private final ClientsRepository clientsRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResponse loginEmploye(LoginRequest request) {
        Acces acces = accesRepository.findByNomUtilisateur(request.getLogin())
                .orElseThrow(() -> new UnauthorizedException("Identifiants invalides"));

        if (!passwordEncoder.matches(request.getMotDePasse(), acces.getMotDePasse())) {
            throw new UnauthorizedException("Identifiants invalides");
        }

        if (acces.getEmploye().getDateFinEmbauche() != null) {
            throw new UnauthorizedException("Compte désactivé");
        }

        String role = acces.getRole().getLibelle();
        String token = jwtUtil.generate(acces.getEmploye().getIdEmploye(), role, "EMPLOYE");

        return LoginResponse.builder()
                .token(token)
                .userType("EMPLOYE")
                .userId(acces.getEmploye().getIdEmploye())
                .role(role)
                .build();
    }

    @Override
    public LoginResponse loginClient(LoginRequest request) {
        Clients client = clientsRepository.findByEmail(request.getLogin())
                .orElseThrow(() -> new UnauthorizedException("Identifiants invalides"));

        if (!passwordEncoder.matches(request.getMotDePasse(), client.getMdp())) {
            throw new UnauthorizedException("Identifiants invalides");
        }

        if (Boolean.FALSE.equals(client.getEstActif())) {
            throw new UnauthorizedException("Compte désactivé");
        }

        String token = jwtUtil.generate(client.getIdClient(), "CLIENT", "CLIENT");

        return LoginResponse.builder()
                .token(token)
                .userType("CLIENT")
                .userId(client.getIdClient())
                .role("CLIENT")
                .build();
    }
}
