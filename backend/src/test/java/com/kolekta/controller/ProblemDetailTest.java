package com.kolekta.controller;

import com.kolekta.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Toutes les erreurs de l'API ont le même format : application/problem+json (RFC 9457). */
@AutoConfigureMockMvc
class ProblemDetailTest extends AbstractIntegrationTest {

    private static final String MDP = "Mdp-De-Test-2026";

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    private String jetonAdmin() throws Exception {
        String login = loginUnique("admin");
        creerEmploye(login, MDP, "admin");
        String reponse = mockMvc.perform(post("/api/auth/employe/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\":\"" + login + "\",\"motDePasse\":\"" + MDP + "\"}"))
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readTree(reponse).get("token").asString();
    }

    @Test
    void validationListeLesChampsFautifs() throws Exception {
        String corps = "{\"nom\":\"\",\"email\":\"pas-un-email\",\"mdp\":\"court\"}";

        mockMvc.perform(post("/api/clients/inscription")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(corps))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.detail").value("Les données envoyées sont invalides"))
                .andExpect(jsonPath("$.errors[*].champ").value(containsInAnyOrder(
                        "nom", "prenoms", "dtn", "email", "telephone", "adresse", "sexeCode", "mdp")));
    }

    @Test
    void ressourceIntrouvableRetourne404() throws Exception {
        mockMvc.perform(get("/api/employes/999999")
                        .header("Authorization", "Bearer " + jetonAdmin()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.instance").value("/api/employes/999999"));
    }

    @Test
    void routeInconnueRetourne404() throws Exception {
        mockMvc.perform(get("/api/nexiste-pas")
                        .header("Authorization", "Bearer " + jetonAdmin()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON));
    }

    @Test
    void jsonIllisibleRetourne400SansDetailInterne() throws Exception {
        mockMvc.perform(post("/api/auth/employe/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{pas du json"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.detail").value("Failed to read request"));
    }

    @Test
    void identifiantsInvalidesRetournent401() throws Exception {
        mockMvc.perform(post("/api/auth/employe/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\":\"inconnu\",\"motDePasse\":\"x\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.detail").value("Identifiants invalides"));
    }
}
