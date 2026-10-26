package com.kolekta.controller;

import com.kolekta.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Connexion, identité (/me) et déconnexion de bout en bout (US-001, US-003, US-082). */
@AutoConfigureMockMvc
class LoginLogoutTest extends AbstractIntegrationTest {

    private static final String MDP = "Mdp-De-Test-2026";

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    private org.springframework.test.web.servlet.ResultActions login(String login, String mdp) throws Exception {
        return mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"login\":\"" + login + "\",\"motDePasse\":\"" + mdp + "\"}"));
    }

    private String jeton(String login) throws Exception {
        String corps = login(login, MDP).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        return objectMapper.readTree(corps).get("token").asString();
    }

    // ---- Connexion ----

    @Test
    void employeActifSeConnecteEtRecoitSonRoleEtSonIdentite() throws Exception {
        String login = loginUnique("gerant");
        creerEmploye(login, MDP, "gerant");

        login(login, MDP)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.role").value("gerant"))
                .andExpect(jsonPath("$.userType").value("EMPLOYE"))
                .andExpect(jsonPath("$.nom").value("Test"))
                .andExpect(jsonPath("$.prenoms").value("Employe"));
    }

    @Test
    void clientActifSeConnecteAvecSonEmail() throws Exception {
        String email = loginUnique("client") + "@example.com";
        creerClient(email, MDP, true);

        login(email, MDP)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("CLIENT"))
                .andExpect(jsonPath("$.userType").value("CLIENT"))
                .andExpect(jsonPath("$.nom").value("Client"));
    }

    @Test
    void mauvaisMotDePasseRetourne401() throws Exception {
        String login = loginUnique("admin");
        creerEmploye(login, MDP, "admin");

        login(login, "mauvais-mot-de-passe")
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.detail").value("Identifiants invalides"));
    }

    @Test
    void identifiantInconnuRetourneLeMemeMessageQueMauvaisMotDePasse() throws Exception {
        login("personne-" + loginUnique("x"), MDP)
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.detail").value("Identifiants invalides"));
    }

    @Test
    void employeDesactiveRetourne403AvecUnMessageExplicite() throws Exception {
        String login = loginUnique("sorti");
        creerEmploye(login, MDP, "collecteur", LocalDate.now().minusDays(1));

        login(login, MDP)
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value("COMPTE_DESACTIVE"))
                .andExpect(jsonPath("$.detail").value("Votre compte employé est désactivé : l'accès est refusé."));
    }

    @Test
    void clientDesactiveRetourne403ContactezKolekta() throws Exception {
        String email = loginUnique("suspendu") + "@example.com";
        creerClient(email, MDP, false);

        login(email, MDP)
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value("COMPTE_DESACTIVE"))
                .andExpect(jsonPath("$.detail").value("Votre compte est désactivé. Contactez Kolekta."));
    }

    @Test
    void connexionSansChampsRetourne400() throws Exception {
        mockMvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest());
    }

    // ---- /me ----

    @Test
    void meAvecJetonRetourneLIdentiteEtLeRole() throws Exception {
        String login = loginUnique("rh");
        creerEmploye(login, MDP, "rh");

        mockMvc.perform(get("/api/auth/me").header("Authorization", "Bearer " + jeton(login)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("rh"))
                .andExpect(jsonPath("$.userType").value("EMPLOYE"))
                .andExpect(jsonPath("$.nom").value("Test"));
    }

    @Test
    void meSansJetonRetourne401() throws Exception {
        mockMvc.perform(get("/api/auth/me"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON));
    }

    @Test
    void meRefuseUnEmployeDesactiveApresSaConnexion() throws Exception {
        String login = loginUnique("parti");
        var acces = creerEmploye(login, MDP, "collecteur");
        String jeton = jeton(login);

        // Désactivation après la connexion : le jeton existant ne doit plus servir
        var employe = acces.getEmploye();
        employe.setDateFinEmbauche(LocalDate.now());
        employesRepository.save(employe);

        mockMvc.perform(get("/api/auth/me").header("Authorization", "Bearer " + jeton))
                .andExpect(status().isUnauthorized());
    }

    // ---- Déconnexion ----

    @Test
    void apresLogoutLeMemeJetonEstRefuse() throws Exception {
        String login = loginUnique("admin");
        creerEmploye(login, MDP, "admin");
        String jeton = jeton(login);

        mockMvc.perform(get("/api/auth/me").header("Authorization", "Bearer " + jeton))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/auth/logout").header("Authorization", "Bearer " + jeton))
                .andExpect(status().isNoContent());

        // Le jeton révoqué est refusé partout, y compris sur une route protégée par rôle
        mockMvc.perform(get("/api/auth/me").header("Authorization", "Bearer " + jeton))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(get("/api/employes").header("Authorization", "Bearer " + jeton))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void logoutNeRevoquePasLesAutresJetons() throws Exception {
        String login = loginUnique("admin");
        creerEmploye(login, MDP, "admin");
        String jetonA = jeton(login);
        String jetonB = jeton(login); // seconde session (autre appareil)

        mockMvc.perform(post("/api/auth/logout").header("Authorization", "Bearer " + jetonA))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/auth/me").header("Authorization", "Bearer " + jetonB))
                .andExpect(status().isOk());
    }

    @Test
    void logoutSansJetonRetourne401() throws Exception {
        mockMvc.perform(post("/api/auth/logout"))
                .andExpect(status().isUnauthorized());
    }
}
