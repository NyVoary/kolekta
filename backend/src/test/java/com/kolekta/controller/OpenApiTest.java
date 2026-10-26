package com.kolekta.controller;

import com.kolekta.AbstractIntegrationTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** La documentation de l'API n'existe que si on l'active (profil dev en vrai ; ici par propriétés). */
class OpenApiTest {

    @AutoConfigureMockMvc
    static class Base extends AbstractIntegrationTest {
        @Autowired MockMvc mockMvc;
    }

    @Nested
    @SpringBootTest(properties = {"springdoc.api-docs.enabled=true", "springdoc.swagger-ui.enabled=true"})
    class Activee extends Base {

        @Test
        void descriptionContientLeSchemaDeSecuriteBearerJwt() throws Exception {
            mockMvc.perform(get("/v3/api-docs"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.components.securitySchemes['Bearer JWT'].scheme").value("bearer"))
                    .andExpect(jsonPath("$.components.securitySchemes['Bearer JWT'].bearerFormat").value("JWT"));
        }

        @Test
        void swaggerUiEstAccessibleSansJeton() throws Exception {
            // /swagger-ui.html redirige vers la page de l'interface
            mockMvc.perform(get("/swagger-ui.html"))
                    .andExpect(status().is3xxRedirection());
        }
    }

    @Nested
    class DesactiveeParDefaut extends Base {

        @Test
        void descriptionFermee() throws Exception {
            // Pas de documentation hors profil dev : la route est fermée (401 sans jeton), plus seulement « introuvable »
            mockMvc.perform(get("/v3/api-docs"))
                    .andExpect(status().isUnauthorized());
        }
    }
}
