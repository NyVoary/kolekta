package com.kolekta.config;

import com.kolekta.service.JetonRevoqueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Nettoyage planifié : un jeton révoqué expiré serait de toute façon refusé, inutile de le garder.
 * S'exécute toutes les heures, à la minute 15.
 */
@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class NettoyageJetonsRevoques {

    private final JetonRevoqueService jetonRevoqueService;

    @Scheduled(cron = "0 15 * * * *")
    public void nettoyer() {
        int supprimes = jetonRevoqueService.purgerExpires();
        if (supprimes > 0) {
            log.info("Nettoyage des jetons révoqués : {} ligne(s) expirée(s) supprimée(s)", supprimes);
        }
    }
}
