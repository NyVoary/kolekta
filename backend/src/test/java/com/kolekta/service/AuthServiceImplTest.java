package com.kolekta.service;

import com.kolekta.dto.auth.LoginRequest;
import com.kolekta.dto.auth.LoginResponse;
import com.kolekta.dto.auth.MeResponse;
import com.kolekta.entity.Acces;
import com.kolekta.entity.Clients;
import com.kolekta.entity.Employes;
import com.kolekta.entity.Roles;
import com.kolekta.exception.CompteDesactiveException;
import com.kolekta.exception.UnauthorizedException;
import com.kolekta.repository.AccesRepository;
import com.kolekta.repository.ClientsRepository;
import com.kolekta.repository.EmployesRepository;
import com.kolekta.security.JwtUtil;
import com.kolekta.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** Test unitaire : aucune base, aucun contexte Spring, tout est simulé avec Mockito. */
@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock AccesRepository accesRepository;
    @Mock ClientsRepository clientsRepository;
    @Mock EmployesRepository employesRepository;
    @Mock PasswordEncoder passwordEncoder;
    @Mock JwtUtil jwtUtil;
    @Mock JetonRevoqueService jetonRevoqueService;

    AuthServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new AuthServiceImpl(accesRepository, clientsRepository, employesRepository,
                passwordEncoder, jwtUtil, jetonRevoqueService);
    }

    private static LoginRequest requete(String login, String motDePasse) {
        LoginRequest r = new LoginRequest();
        r.setLogin(login);
        r.setMotDePasse(motDePasse);
        return r;
    }

    private static Employes employe(LocalDate dateFin) {
        Employes employe = new Employes();
        employe.setIdEmploye(7L);
        employe.setNom("Rakoto");
        employe.setPrenoms("Paul");
        employe.setDateFinEmbauche(dateFin);
        return employe;
    }

    private static Acces acces(Employes employe) {
        Roles role = new Roles();
        role.setLibelle("gerant");
        Acces acces = new Acces();
        acces.setEmploye(employe);
        acces.setRole(role);
        acces.setNomUtilisateur("paul");
        acces.setMotDePasse("hash");
        return acces;
    }

    /** Prépare un accès existant dont le mot de passe haché est « hash » ; dateFin peut être null. */
    private void accesAvecDateFin(LocalDate dateFin) {
        when(accesRepository.findByNomUtilisateur("paul")).thenReturn(Optional.of(acces(employe(dateFin))));
    }

    private void clientExistant(boolean actif) {
        Clients client = new Clients();
        client.setIdClient(3L);
        client.setNom("Rabe");
        client.setPrenoms("Alice");
        client.setEmail("alice@example.com");
        client.setMdp("hash");
        client.setEstActif(actif);
        when(accesRepository.findByNomUtilisateur("alice@example.com")).thenReturn(Optional.empty());
        when(clientsRepository.findByEmail("alice@example.com")).thenReturn(Optional.of(client));
    }

    // ---- Connexion employé ----

    @Test
    void employeActifPeutSeConnecter() {
        accesAvecDateFin(null);
        when(passwordEncoder.matches("secret", "hash")).thenReturn(true);
        when(jwtUtil.generate(7L, "gerant", "EMPLOYE")).thenReturn("jeton");

        LoginResponse reponse = service.login(requete("paul", "secret"));

        assertThat(reponse.getToken()).isEqualTo("jeton");
        assertThat(reponse.getRole()).isEqualTo("gerant");
        assertThat(reponse.getUserType()).isEqualTo("EMPLOYE");
        assertThat(reponse.getUserId()).isEqualTo(7L);
        assertThat(reponse.getNom()).isEqualTo("Rakoto");
        assertThat(reponse.getPrenoms()).isEqualTo("Paul");
    }

    @Test
    void employeAvecDateFinDansLePasseEstRefuse() {
        accesAvecDateFin(LocalDate.now().minusDays(1));
        when(passwordEncoder.matches("secret", "hash")).thenReturn(true);

        assertThatThrownBy(() -> service.login(requete("paul", "secret")))
                .isInstanceOf(CompteDesactiveException.class)
                .hasMessageContaining("désactivé");
        verify(jwtUtil, never()).generate(anyLong(), anyString(), anyString());
    }

    @Test
    void employeAvecDateFinAujourdhuiEstRefuse() {
        accesAvecDateFin(LocalDate.now());
        when(passwordEncoder.matches("secret", "hash")).thenReturn(true);

        assertThatThrownBy(() -> service.login(requete("paul", "secret")))
                .isInstanceOf(CompteDesactiveException.class);
    }

    @Test
    void employeAvecDateFinFutureEstAutorise() {
        accesAvecDateFin(LocalDate.now().plusDays(1));
        when(passwordEncoder.matches("secret", "hash")).thenReturn(true);
        when(jwtUtil.generate(7L, "gerant", "EMPLOYE")).thenReturn("jeton");

        assertThat(service.login(requete("paul", "secret")).getToken()).isEqualTo("jeton");
    }

    @Test
    void mauvaisMotDePasseEstRefuse() {
        accesAvecDateFin(null);
        when(passwordEncoder.matches("faux", "hash")).thenReturn(false);

        assertThatThrownBy(() -> service.login(requete("paul", "faux")))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessage("Identifiants invalides");
        verify(jwtUtil, never()).generate(anyLong(), anyString(), anyString());
    }

    @Test
    void employeDesactiveAvecMauvaisMotDePasseNeRevelePasSonEtat() {
        // Mauvais mot de passe : message générique, on ne dit pas que le compte est désactivé
        accesAvecDateFin(LocalDate.now().minusDays(1));
        when(passwordEncoder.matches("faux", "hash")).thenReturn(false);

        assertThatThrownBy(() -> service.login(requete("paul", "faux")))
                .isInstanceOf(UnauthorizedException.class);
    }

    // ---- Connexion client ----

    @Test
    void clientActifPeutSeConnecter() {
        clientExistant(true);
        when(passwordEncoder.matches("secret", "hash")).thenReturn(true);
        when(jwtUtil.generate(3L, "CLIENT", "CLIENT")).thenReturn("jeton-client");

        LoginResponse reponse = service.login(requete("alice@example.com", "secret"));

        assertThat(reponse.getToken()).isEqualTo("jeton-client");
        assertThat(reponse.getUserType()).isEqualTo("CLIENT");
        assertThat(reponse.getRole()).isEqualTo("CLIENT");
        assertThat(reponse.getNom()).isEqualTo("Rabe");
    }

    @Test
    void clientDesactiveRecoitLeMessageContactezKolekta() {
        clientExistant(false);
        when(passwordEncoder.matches("secret", "hash")).thenReturn(true);

        assertThatThrownBy(() -> service.login(requete("alice@example.com", "secret")))
                .isInstanceOf(CompteDesactiveException.class)
                .hasMessageContaining("Contactez Kolekta");
    }

    @Test
    void identifiantInconnuRetourneLeMessageGenerique() {
        when(accesRepository.findByNomUtilisateur("inconnu")).thenReturn(Optional.empty());
        when(clientsRepository.findByEmail("inconnu")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.login(requete("inconnu", "x")))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessage("Identifiants invalides");
    }

    // ---- /me et déconnexion ----

    @Test
    void meRetourneLIdentiteDUnEmployeActif() {
        Employes employe = employe(null);
        when(employesRepository.findById(7L)).thenReturn(Optional.of(employe));
        when(accesRepository.findFirstByEmploye_IdEmploye(7L)).thenReturn(Optional.of(acces(employe)));

        MeResponse me = service.me(7L, "EMPLOYE");

        assertThat(me.getRole()).isEqualTo("gerant");
        assertThat(me.getNom()).isEqualTo("Rakoto");
        assertThat(me.getUserType()).isEqualTo("EMPLOYE");
    }

    @Test
    void meRefuseUnEmployeDevenuInactif() {
        when(employesRepository.findById(7L)).thenReturn(Optional.of(employe(LocalDate.now().minusDays(1))));

        assertThatThrownBy(() -> service.me(7L, "EMPLOYE")).isInstanceOf(UnauthorizedException.class);
    }

    @Test
    void meRefuseUnClientDevenuInactif() {
        Clients client = new Clients();
        client.setEstActif(false);
        when(clientsRepository.findById(3L)).thenReturn(Optional.of(client));

        assertThatThrownBy(() -> service.me(3L, "CLIENT")).isInstanceOf(UnauthorizedException.class);
    }

    @Test
    void logoutRevoqueLeJeton() {
        Instant expiration = Instant.now().plusSeconds(600);

        service.logout("un-jti", expiration);

        verify(jetonRevoqueService).revoquer("un-jti", expiration);
    }
}
