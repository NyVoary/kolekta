package com.kolekta.controller;

import com.kolekta.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Refus par défaut : un endpoint qui n'a PAS de règle dans SecurityConfig est fermé, même pour l'Admin.
 * L'endpoint ci-dessous n'existe que dans ce test (il n'est jamais ajouté à l'application) et il
 * « oublie » volontairement de se déclarer.
 */
@AutoConfigureMockMvc
@Import(DenyByDefaultTest.EndpointSansRegle.class)
class DenyByDefaultTest extends AbstractIntegrationTest {

    private static final String MDP = "Mdp-De-Test-2026";

    @RestController
    static class EndpointSansRegle {
        @GetMapping("/api/test-sans-regle")
        String lire() {
            return "ouvert";
        }

        @PostMapping("/api/test-sans-regle")
        String ecrire() {
            return "ouvert";
        }
    }

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

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

    @ParameterizedTest(name = "{0} sur un endpoint sans règle -> 403")
    @ValueSource(strings = {"admin", "gerant", "rh", "collecteur", "client"})
    void unEndpointSansRegleEstFermeMemePourLAdmin(String role) throws Exception {
        mockMvc.perform(get("/api/test-sans-regle").header("Authorization", "Bearer " + jetonPour(role)))
                .andExpect(status().isForbidden())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status").value(403));
    }

    @Test
    void toutesLesMethodesSontFermees() throws Exception {
        mockMvc.perform(post("/api/test-sans-regle").header("Authorization", "Bearer " + jetonPour("admin")))
                .andExpect(status().isForbidden());
    }

    @Test
    void sansJetonUnEndpointSansRegleRepond401EnProblemDetail() throws Exception {
        mockMvc.perform(get("/api/test-sans-regle"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status").value(401));
    }

    @Test
    void uneRouteQuiNExistePasEstAussiFermee() throws Exception {
        // La sécurité s'applique avant la recherche du contrôleur : on ne révèle pas ce qui existe
        mockMvc.perform(get("/api/nexiste-pas").header("Authorization", "Bearer " + jetonPour("admin")))
                .andExpect(status().isForbidden());
    }

    @Test
    void swaggerResteFermeQuandLaDocumentationNEstPasActivee() throws Exception {
        // Profil non-dev : springdoc.*.enabled=false (valeur par défaut). Fermé, même pour l'Admin.
        String admin = jetonPour("admin");
        for (String route : new String[]{"/v3/api-docs", "/swagger-ui.html", "/swagger-ui/index.html"}) {
            mockMvc.perform(get(route)).andExpect(status().isUnauthorized());
            mockMvc.perform(get(route).header("Authorization", "Bearer " + admin))
                    .andExpect(status().isForbidden());
        }
    }
}
