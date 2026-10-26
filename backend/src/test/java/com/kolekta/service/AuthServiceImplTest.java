package com.kolekta.service;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kolekta.dto.auth.LoginRequest;
import com.kolekta.dto.auth.LoginResponse;
import com.kolekta.entity.Acces;
import com.kolekta.entity.Employes;
import com.kolekta.entity.Roles;
import com.kolekta.exception.UnauthorizedException;
import com.kolekta.repository.AccesRepository;
import com.kolekta.repository.ClientsRepository;
import com.kolekta.security.JwtUtil;
import com.kolekta.service.impl.AuthServiceImpl;

/** Test unitaire : aucune base, aucun contexte Spring, tout est simulé avec Mockito. */
@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock AccesRepository accesRepository;
    @Mock ClientsRepository clientsRepository;
    @Mock PasswordEncoder passwordEncoder;
    @Mock JwtUtil jwtUtil;

    AuthServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new AuthServiceImpl(accesRepository, clientsRepository, passwordEncoder, jwtUtil);
    }

    private static LoginRequest requete(String login, String motDePasse) {
        LoginRequest r = new LoginRequest();
        r.setLogin(login);
        r.setMotDePasse(motDePasse);
        return r;
    }

    /** Prépare un accès existant dont le mot de passe haché est « hash » ; dateFin peut être null. */
    private void accesAvecDateFin(LocalDate dateFin) {
        Employes employe = new Employes();
        employe.setIdEmploye(7L);
        employe.setDateFinEmbauche(dateFin);
        Roles role = new Roles();
        role.setLibelle("gerant");
        Acces acces = new Acces();
        acces.setEmploye(employe);
        acces.setRole(role);
        acces.setNomUtilisateur("paul");
        acces.setMotDePasse("hash");
        when(accesRepository.findByNomUtilisateur("paul")).thenReturn(Optional.of(acces));
    }

    @Test
    void employeActifPeutSeConnecter() {
        accesAvecDateFin(null);
        when(passwordEncoder.matches("secret", "hash")).thenReturn(true);
        when(jwtUtil.generate(7L, "gerant", "EMPLOYE")).thenReturn("jeton");

        LoginResponse reponse = service.loginEmploye(requete("paul", "secret"));

        assertThat(reponse.getToken()).isEqualTo("jeton");
        assertThat(reponse.getRole()).isEqualTo("gerant");
        assertThat(reponse.getUserType()).isEqualTo("EMPLOYE");
        assertThat(reponse.getUserId()).isEqualTo(7L);
    }

    @Test
    void employeAvecDateFinDansLePasseEstRefuse() {
        accesAvecDateFin(LocalDate.now().minusDays(1));
        when(passwordEncoder.matches("secret", "hash")).thenReturn(true);

        assertThatThrownBy(() -> service.loginEmploye(requete("paul", "secret")))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessage("Compte désactivé");
        verify(jwtUtil, never()).generate(anyLong(), anyString(), anyString());
    }

    @Test
    void employeAvecDateFinAujourdhuiEstRefuse() {
        accesAvecDateFin(LocalDate.now());
        when(passwordEncoder.matches("secret", "hash")).thenReturn(true);

        assertThatThrownBy(() -> service.loginEmploye(requete("paul", "secret")))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessage("Compte désactivé");
    }

    @Test
    void employeAvecDateFinFutureEstAutorise() {
        accesAvecDateFin(LocalDate.now().plusDays(1));
        when(passwordEncoder.matches("secret", "hash")).thenReturn(true);
        when(jwtUtil.generate(7L, "gerant", "EMPLOYE")).thenReturn("jeton");

        assertThat(service.loginEmploye(requete("paul", "secret")).getToken()).isEqualTo("jeton");
    }

    @Test
    void mauvaisMotDePasseEstRefuse() {
        accesAvecDateFin(null);
        when(passwordEncoder.matches("faux", "hash")).thenReturn(false);

        assertThatThrownBy(() -> service.loginEmploye(requete("paul", "faux")))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessage("Identifiants invalides");
        verify(jwtUtil, never()).generate(anyLong(), anyString(), anyString());
    }
}
