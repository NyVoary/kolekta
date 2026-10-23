package com.kolekta.controller;

import com.kolekta.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** CORS configurable par CORS_ALLOWED_ORIGINS (ici deux origines de test) et en-têtes de sécurité. */
@AutoConfigureMockMvc
@SpringBootTest(properties = "app.cors.allowed-origins=http://localhost:5173,https://kolekta.example.com")
class CorsAndHeadersTest extends AbstractIntegrationTest {

    @Autowired MockMvc mockMvc;

    private org.springframework.test.web.servlet.ResultActions preflight(String origin) throws Exception {
        return mockMvc.perform(options("/api/employes")
                .header("Origin", origin)
                .header("Access-Control-Request-Method", "GET")
                .header("Access-Control-Request-Headers", "authorization"));
    }

    @Test
    void preflightDUneOrigineAutoriseeSansJeton() throws Exception {
        preflight("https://kolekta.example.com")
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", "https://kolekta.example.com"));
    }

    @Test
    void frontendDeDevAutoriseDansLaListe() throws Exception {
        preflight("http://localhost:5173")
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:5173"));
    }

    @Test
    void origineNonListeeEstRefusee() throws Exception {
        preflight("https://pirate.example.org")
                .andExpect(status().isForbidden())
                .andExpect(header().doesNotExist("Access-Control-Allow-Origin"));
    }

    @Test
    void enTetesDeSecuriteSontPresents() throws Exception {
        mockMvc.perform(post("/api/auth/employe/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\":\"x\",\"motDePasse\":\"y\"}"))
                .andExpect(header().string("X-Content-Type-Options", "nosniff"))
                .andExpect(header().string("X-Frame-Options", "DENY"))
                .andExpect(header().string("Referrer-Policy", "no-referrer"))
                .andExpect(header().string("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate"));
    }
}
