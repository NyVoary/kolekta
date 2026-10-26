package com.kolekta.config;

import com.kolekta.security.JwtAuthFilter;
import com.kolekta.security.RestSecurityErrorHandlers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] ROLES_EMPLOYES = {"ADMIN", "GERANT", "RH"};
    private static final String[] ROLES_CLIENTS = {"ADMIN", "GERANT"};

    private final JwtAuthFilter jwtAuthFilter;
    private final RestSecurityErrorHandlers errorHandlers;

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            @Value("${springdoc.api-docs.enabled:false}") boolean apiDocsActive,
            @Value("${springdoc.swagger-ui.enabled:false}") boolean swaggerUiActive) throws Exception {
        http
            // API sans session ni cookie (jeton dans l'en-tête Authorization) : pas de CSRF à craindre
            .csrf(AbstractHttpConfigurer::disable)
            // Utilise le bean CorsConfigurationSource ci-dessous ; traite aussi les requêtes OPTIONS (preflight)
            .cors(Customizer.withDefaults())
            // En plus des en-têtes par défaut de Spring Security (nosniff, X-Frame-Options, no-cache...)
            .headers(h -> h.referrerPolicy(r -> r.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.NO_REFERRER)))
            // 401 (jeton absent/invalide) et 403 (rôle insuffisant) au format ProblemDetail
            .exceptionHandling(e -> e
                .authenticationEntryPoint(errorHandlers)
                .accessDeniedHandler(errorHandlers))
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> {
                // ---- Documentation : ouverte seulement quand elle est activée (profil dev), sinon fermée ----
                if (apiDocsActive) {
                    auth.requestMatchers(HttpMethod.GET, "/v3/api-docs", "/v3/api-docs/**").permitAll();
                }
                if (swaggerUiActive) {
                    auth.requestMatchers(HttpMethod.GET, "/swagger-ui.html", "/swagger-ui/**").permitAll();
                }

                // ---- Routes publiques (aucun jeton) ----
                auth.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll();
                auth.requestMatchers(HttpMethod.POST, "/api/clients/inscription").permitAll();

                // ---- Tout compte connecté (employé ou client) ----
                auth.requestMatchers(HttpMethod.GET, "/api/auth/me").authenticated();
                auth.requestMatchers(HttpMethod.POST, "/api/auth/logout").authenticated();

                // ---- Employés : Admin, Gérant, RH (EmployeController) ----
                auth.requestMatchers(HttpMethod.GET, "/api/employes", "/api/employes/{id}").hasAnyRole(ROLES_EMPLOYES);
                auth.requestMatchers(HttpMethod.POST, "/api/employes", "/api/employes/acces").hasAnyRole(ROLES_EMPLOYES);
                auth.requestMatchers(HttpMethod.PUT, "/api/employes/{id}").hasAnyRole(ROLES_EMPLOYES);
                auth.requestMatchers(HttpMethod.DELETE, "/api/employes/{id}").hasAnyRole(ROLES_EMPLOYES);

                // ---- Clients : Admin, Gérant (ClientController, hors inscription publique ci-dessus) ----
                auth.requestMatchers(HttpMethod.GET, "/api/clients", "/api/clients/{id}").hasAnyRole(ROLES_CLIENTS);
                auth.requestMatchers(HttpMethod.PUT, "/api/clients/{id}").hasAnyRole(ROLES_CLIENTS);
                auth.requestMatchers(HttpMethod.DELETE, "/api/clients/{id}").hasAnyRole(ROLES_CLIENTS);

                // ---- REFUS PAR DÉFAUT : tout le reste est fermé, même pour l'Admin. ----
                // Un nouvel endpoint doit être ajouté ci-dessus avec ses rôles (et testé en 200 et en 403).
                // Les requêtes OPTIONS (CORS) sont traitées plus haut par le filtre CORS, sans passer ici.
                auth.anyRequest().denyAll();
            })
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /** Origines autorisées : variable CORS_ALLOWED_ORIGINS (séparées par des virgules). */
    @Bean
    public CorsConfigurationSource corsConfigurationSource(
            @Value("${app.cors.allowed-origins}") List<String> allowedOrigins) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(allowedOrigins);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE, HttpHeaders.ACCEPT));
        config.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
