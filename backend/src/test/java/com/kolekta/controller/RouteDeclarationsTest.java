package com.kolekta.controller;

import com.kolekta.AbstractIntegrationTest;
import com.kolekta.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;

/**
 * Garde-fou : chaque endpoint de l'application doit être déclaré dans SecurityConfig.
 *
 * Le test parcourt tous les contrôleurs, appelle chaque route en tant qu'Admin et vérifie qu'elle n'est pas
 * fermée par le refus par défaut (403). Si quelqu'un ajoute un endpoint sans le déclarer dans SecurityConfig,
 * ce test échoue et nomme la route oubliée.
 *
 * Les appels sont sans effet : identifiants inexistants (999999) et corps vide, donc 400 ou 404, jamais de
 * création ni de suppression. Si une route est volontairement interdite à l'Admin, l'ajouter à EXCEPTIONS.
 */
@AutoConfigureMockMvc
class RouteDeclarationsTest extends AbstractIntegrationTest {

    /** Routes volontairement fermées à l'Admin (aucune aujourd'hui). Format : « GET /api/exemple ». */
    private static final Set<String> EXCEPTIONS = Set.of();

    @Autowired MockMvc mockMvc;
    @Autowired RequestMappingHandlerMapping handlerMapping;
    @Autowired JwtUtil jwtUtil;

    @Test
    void toutesLesRoutesDesControleursSontDeclareesDansSecurityConfig() throws Exception {
        Long idAdmin = creerEmploye(loginUnique("admin"), "Mdp-De-Test-2026", "admin").getEmploye().getIdEmploye();
        List<String> oubliees = new ArrayList<>();
        int verifiees = 0;

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entree : handlerMapping.getHandlerMethods().entrySet()) {
            // Seulement nos contrôleurs (pas /error, ni Swagger, ni un contrôleur défini dans un test)
            String classe = entree.getValue().getBeanType().getName();
            if (!classe.startsWith("com.kolekta.controller.") || classe.contains("Test")) continue;

            var methodes = entree.getKey().getMethodsCondition().getMethods();
            var chemins = entree.getKey().getPathPatternsCondition().getPatternValues();
            for (RequestMethod methode : methodes.isEmpty() ? Set.of(RequestMethod.GET) : methodes) {
                for (String chemin : chemins) {
                    String route = methode + " " + chemin;
                    if (EXCEPTIONS.contains(route)) continue;

                    // Un jeton Admin par appel : un POST /logout révoque son jeton, il ne doit pas gêner les autres
                    String jeton = jwtUtil.generate(idAdmin, "admin", "EMPLOYE");
                    MvcResult resultat = mockMvc.perform(request(HttpMethod.valueOf(methode.name()),
                                    chemin.replace("{id}", "999999"))
                                    .header("Authorization", "Bearer " + jeton)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("{}"))
                            .andReturn();
                    verifiees++;
                    if (resultat.getResponse().getStatus() == 403) oubliees.add(route);
                }
            }
        }

        assertThat(verifiees).as("des routes ont bien été parcourues").isGreaterThanOrEqualTo(14);
        assertThat(oubliees)
                .as("routes fermées par le refus par défaut : à déclarer dans SecurityConfig avec leurs rôles")
                .isEmpty();
    }
}
