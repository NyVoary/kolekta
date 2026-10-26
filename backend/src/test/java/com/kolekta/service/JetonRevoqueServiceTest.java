package com.kolekta.service;

import com.kolekta.AbstractIntegrationTest;
import com.kolekta.repository.JetonRevoqueRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/** Révocation et nettoyage des jetons, sur une vraie base. */
class JetonRevoqueServiceTest extends AbstractIntegrationTest {

    @Autowired JetonRevoqueService service;
    @Autowired JetonRevoqueRepository repository;

    private static String nouveauJti() {
        return UUID.randomUUID().toString();
    }

    @Test
    void unJetonRevoqueEstReconnuEtUnAutreNon() {
        String revoque = nouveauJti();
        service.revoquer(revoque, Instant.now().plusSeconds(3600));

        assertThat(service.estRevoque(revoque)).isTrue();
        assertThat(service.estRevoque(nouveauJti())).isFalse();
        assertThat(service.estRevoque(null)).isFalse();
    }

    @Test
    void revoquerDeuxFoisLeMemeJetonNeCausePasDErreur() {
        String jti = nouveauJti();
        Instant expiration = Instant.now().plusSeconds(3600);

        service.revoquer(jti, expiration);
        service.revoquer(jti, expiration);

        assertThat(repository.findById(jti)).isPresent();
    }

    @Test
    void leNettoyageSupprimeLesJetonsExpiresEtGardeLesAutres() {
        String expire = nouveauJti();
        String valide = nouveauJti();
        service.revoquer(expire, Instant.now().minusSeconds(60));
        service.revoquer(valide, Instant.now().plusSeconds(3600));

        int supprimes = service.purgerExpires();

        assertThat(supprimes).isGreaterThanOrEqualTo(1);
        assertThat(repository.existsById(expire)).isFalse();
        assertThat(repository.existsById(valide)).isTrue();
    }
}
