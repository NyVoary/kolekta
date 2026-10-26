package com.kolekta.service.impl;

import com.kolekta.dto.auth.LoginRequest;
import com.kolekta.dto.auth.LoginResponse;
import com.kolekta.dto.auth.MeResponse;
import com.kolekta.entity.Acces;
import com.kolekta.entity.Clients;
import com.kolekta.entity.Employes;
import com.kolekta.exception.CompteDesactiveException;
import com.kolekta.exception.UnauthorizedException;
import com.kolekta.repository.AccesRepository;
import com.kolekta.repository.ClientsRepository;
import com.kolekta.repository.EmployesRepository;
import com.kolekta.security.JwtUtil;
import com.kolekta.service.AuthService;
import com.kolekta.service.JetonRevoqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    static final String EMPLOYE = "EMPLOYE";
    static final String CLIENT = "CLIENT";

    // Message générique : il ne dit jamais si c'est l'identifiant ou le mot de passe qui est faux (US-001)
    static final String IDENTIFIANTS_INVALIDES = "Identifiants invalides";
    static final String EMPLOYE_DESACTIVE = "Votre compte employé est désactivé : l'accès est refusé.";
    static final String CLIENT_DESACTIVE = "Votre compte est désactivé. Contactez Kolekta.";

    private final AccesRepository accesRepository;
    private final ClientsRepository clientsRepository;
    private final EmployesRepository employesRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final JetonRevoqueService jetonRevoqueService;

    @Override
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        Optional<Acces> acces = accesRepository.findByNomUtilisateur(request.getLogin());
        if (acces.isPresent()) {
            return loginEmploye(acces.get(), request.getMotDePasse());
        }
        Optional<Clients> client = clientsRepository.findByEmail(request.getLogin());
        if (client.isPresent()) {
            return loginClient(client.get(), request.getMotDePasse());
        }
        throw new UnauthorizedException(IDENTIFIANTS_INVALIDES);
    }

    private LoginResponse loginEmploye(Acces acces, String motDePasse) {
        // Le mot de passe est vérifié AVANT l'état du compte : on ne révèle l'état qu'à son propriétaire
        if (!passwordEncoder.matches(motDePasse, acces.getMotDePasse())) {
            throw new UnauthorizedException(IDENTIFIANTS_INVALIDES);
        }
        Employes employe = acces.getEmploye();
        if (!estEmployeActif(employe)) {
            throw new CompteDesactiveException(EMPLOYE_DESACTIVE);
        }

        String role = acces.getRole().getLibelle();
        return LoginResponse.builder()
                .token(jwtUtil.generate(employe.getIdEmploye(), role, EMPLOYE))
                .userType(EMPLOYE)
                .userId(employe.getIdEmploye())
                .role(role)
                .nom(employe.getNom())
                .prenoms(employe.getPrenoms())
                .build();
    }

    private LoginResponse loginClient(Clients client, String motDePasse) {
        if (client.getMdp() == null || !passwordEncoder.matches(motDePasse, client.getMdp())) {
            throw new UnauthorizedException(IDENTIFIANTS_INVALIDES);
        }
        if (!estClientActif(client)) {
            throw new CompteDesactiveException(CLIENT_DESACTIVE);
        }

        return LoginResponse.builder()
                .token(jwtUtil.generate(client.getIdClient(), "CLIENT", CLIENT))
                .userType(CLIENT)
                .userId(client.getIdClient())
                .role("CLIENT")
                .nom(client.getNom())
                .prenoms(client.getPrenoms())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public MeResponse me(Long userId, String userType) {
        if (EMPLOYE.equals(userType)) {
            Employes employe = employesRepository.findById(userId)
                    .orElseThrow(() -> new UnauthorizedException("Session invalide"));
            if (!estEmployeActif(employe)) {
                throw new UnauthorizedException("Session invalide : compte désactivé");
            }
            // Le rôle est relu en base : il a pu changer depuis la connexion
            Acces acces = accesRepository.findFirstByEmploye_IdEmploye(userId)
                    .orElseThrow(() -> new UnauthorizedException("Session invalide"));
            return MeResponse.builder()
                    .userId(userId).userType(EMPLOYE).role(acces.getRole().getLibelle())
                    .nom(employe.getNom()).prenoms(employe.getPrenoms())
                    .build();
        }
        if (CLIENT.equals(userType)) {
            Clients client = clientsRepository.findById(userId)
                    .orElseThrow(() -> new UnauthorizedException("Session invalide"));
            if (!estClientActif(client)) {
                throw new UnauthorizedException("Session invalide : compte désactivé");
            }
            return MeResponse.builder()
                    .userId(userId).userType(CLIENT).role("CLIENT")
                    .nom(client.getNom()).prenoms(client.getPrenoms())
                    .build();
        }
        throw new UnauthorizedException("Session invalide");
    }

    @Override
    public void logout(String jti, Instant expireLe) {
        jetonRevoqueService.revoquer(jti, expireLe);
    }

    // Un employé n'est désactivé que si sa date de fin d'embauche est renseignée ET déjà atteinte
    private static boolean estEmployeActif(Employes employe) {
        LocalDate dateFin = employe.getDateFinEmbauche();
        return dateFin == null || dateFin.isAfter(LocalDate.now());
    }

    private static boolean estClientActif(Clients client) {
        return !Boolean.FALSE.equals(client.getEstActif());
    }
}
