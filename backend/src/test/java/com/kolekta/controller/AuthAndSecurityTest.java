package com.kolekta.controller;

import com.kolekta.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test de bout en bout (requête HTTP simulée -> sécurité -> contrôleur -> service -> vraie base).
 * Le nom finit par Test : Maven Surefire l'exécute avec les autres tests (un nom en IT ne le serait pas).
 */
@AutoConfigureMockMvc
class AuthAndSecurityTest extends AbstractIntegrationTest {

    private static final String MDP = "Mdp-De-Test-2026";

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    String loginAdmin;
    String loginCollecteur;

    @BeforeEach
    void creerLesComptes() {
        loginAdmin = loginUnique("admin");
        loginCollecteur = loginUnique("collecteur");
        creerEmploye(loginAdmin, MDP, "admin");
        creerEmploye(loginCollecteur, MDP, "collecteur");
    }

    private String json(String login, String mdp) {
        return "{\"login\":\"" + login + "\",\"motDePasse\":\"" + mdp + "\"}";
    }

    private String connecter(String login) throws Exception {
        MvcResult res = mockMvc.perform(post("/api/auth/employe/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(login, MDP)))
                .andExpect(status().isOk())
                .andReturn();
        JsonNode corps = objectMapper.readTree(res.getResponse().getContentAsString());
        return corps.get("token").asString();
    }

    @Test
    void connexionReussieRetourneUnJeton() throws Exception {
        mockMvc.perform(post("/api/auth/employe/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(loginAdmin, MDP)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.role").value("admin"))
                .andExpect(jsonPath("$.userType").value("EMPLOYE"));
    }

    @Test
    void mauvaisMotDePasseRetourne401() throws Exception {
        mockMvc.perform(post("/api/auth/employe/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(loginAdmin, "mauvais-mot-de-passe")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void listeDesEmployesSansJeton() throws Exception {
        // Aujourd'hui Spring Security répond 403 sans jeton ; ce sera 401 au ticket S0-8
        // (alors, remplacer isForbidden() par isUnauthorized()).
        mockMvc.perform(get("/api/employes"))
                .andExpect(status().isForbidden());
    }

    @Test
    void listeDesEmployesAvecJetonAdmin() throws Exception {
        mockMvc.perform(get("/api/employes")
                        .header("Authorization", "Bearer " + connecter(loginAdmin)))
                .andExpect(status().isOk());
    }

    @Test
    void listeDesEmployesAvecJetonCollecteurRetourne403() throws Exception {
        mockMvc.perform(get("/api/employes")
                        .header("Authorization", "Bearer " + connecter(loginCollecteur)))
                .andExpect(status().isForbidden());
    }

    @Test
    void inscriptionClientSansJetonRetourne201() throws Exception {
        String suffixe = loginUnique("c").substring(2);
        String corps = """
                {"nom":"Test","prenoms":"Client","dtn":"1995-05-05",
                 "email":"client-%s@example.com","telephone":"034-%s",
                 "adresse":"Antsirabe","sexeCode":"F","mdp":"Mdp-De-Test-2026"}
                """.formatted(suffixe, suffixe);

        mockMvc.perform(post("/api/clients/inscription")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(corps))
                .andExpect(status().isCreated());
    }

    @Test
    void inscriptionClientAvecDonneesInvalidesRetourne400() throws Exception {
        // E-mail invalide, mot de passe trop court, champs obligatoires manquants
        String corps = "{\"nom\":\"\",\"email\":\"pas-un-email\",\"mdp\":\"court\"}";

        mockMvc.perform(post("/api/clients/inscription")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(corps))
                .andExpect(status().isBadRequest());
    }
}
