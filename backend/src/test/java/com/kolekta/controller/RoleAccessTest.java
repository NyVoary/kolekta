package com.kolekta.controller;

import com.kolekta.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Le contrôle des rôles se fait côté API (403), pas seulement côté écran : même si quelqu'un contourne
 * l'interface (curl, Swagger), l'API refuse. Chaque ligne = un rôle, une route, le code attendu.
 */
@AutoConfigureMockMvc
class RoleAccessTest extends AbstractIntegrationTest {

    private static final String MDP = "Mdp-De-Test-2026";

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    /** Connecte une personne de ce rôle (employé, ou client si role = client) et retourne son jeton. */
    private String jetonPour(String role) throws Exception {
        String login;
        if ("client".equals(role)) {
            login = loginUnique("client") + "@example.com";
            creerClient(login, MDP, true);
        } else {
            login = loginUnique(role);
            creerEmploye(login, MDP, role);
        }
        String reponse = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\":\"" + login + "\",\"motDePasse\":\"" + MDP + "\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readTree(reponse).get("token").asString();
    }

    @ParameterizedTest(name = "{0} sur GET {1} -> {2}")
    @CsvSource({
            // Liste des employés : admin, gérant et RH seulement
            "admin,      /api/employes, 200",
            "gerant,     /api/employes, 200",
            "rh,         /api/employes, 200",
            "collecteur, /api/employes, 403",
            "client,     /api/employes, 403",
            // Liste des clients : admin et gérant seulement (le RH et le collecteur n'y ont pas accès)
            "admin,      /api/clients,  200",
            "gerant,     /api/clients,  200",
            "rh,         /api/clients,  403",
            "collecteur, /api/clients,  403",
            "client,     /api/clients,  403",
            // Identité de la personne connectée : tout le monde, une fois connecté
            "admin,      /api/auth/me,  200",
            "rh,         /api/auth/me,  200",
            "collecteur, /api/auth/me,  200",
            "client,     /api/auth/me,  200",
    })
    void accesSelonLeRole(String role, String route, int statutAttendu) throws Exception {
        mockMvc.perform(get(route).header("Authorization", "Bearer " + jetonPour(role)))
                .andExpect(status().is(statutAttendu));
    }

    @Test
    void sansJetonLesRoutesProtegeesRepondent401() throws Exception {
        for (String route : new String[]{"/api/employes", "/api/clients", "/api/auth/me"}) {
            mockMvc.perform(get(route)).andExpect(status().isUnauthorized());
        }
    }
}
