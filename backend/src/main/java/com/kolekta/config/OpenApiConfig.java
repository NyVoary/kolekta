package com.kolekta.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description de l'API pour Swagger UI. N'existe que si springdoc.api-docs.enabled=true,
 * c'est-à-dire avec le profil dev : un seul interrupteur pour toute la documentation.
 */
@Configuration
@ConditionalOnProperty(name = "springdoc.api-docs.enabled", havingValue = "true")
public class OpenApiConfig {

    public static final String BEARER_JWT = "Bearer JWT";

    @Bean
    public OpenAPI kolektaOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Kolekta API")
                        .description("API REST de Kolekta. Connecte-toi via /api/auth/..., "
                                + "puis clique sur « Authorize » et colle le jeton reçu.")
                        .version("1.0.0"))
                .components(new Components().addSecuritySchemes(BEARER_JWT,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                // Appliqué à tous les endpoints ; les routes publiques ignorent simplement le jeton
                .addSecurityItem(new SecurityRequirement().addList(BEARER_JWT));
    }
}
