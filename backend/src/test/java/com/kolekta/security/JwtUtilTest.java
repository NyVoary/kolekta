package com.kolekta.security;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtUtilTest {

    @Test
    void secretDeMoinsDe32OctetsEstRefuse() {
        String court = "x".repeat(31);

        assertThatThrownBy(() -> new JwtUtil(court, 3600000))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("32 octets")
                .hasMessageNotContaining(court);
    }

    @Test
    void secretDeCaracteresAccentuesEstCompteEnOctets() {
        // 16 caractères mais 32 octets en UTF-8 : accepté
        assertThat(new JwtUtil("é".repeat(16), 3600000)).isNotNull();
        // 31 octets : refusé
        assertThatThrownBy(() -> new JwtUtil("é".repeat(15) + "a", 3600000))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void secretDe32OctetsEstAccepteEtLeJetonFaitLAllerRetour() {
        JwtUtil jwt = new JwtUtil("x".repeat(32), 3600000);

        String token = jwt.generate(5L, "admin", "EMPLOYE");

        assertThat(jwt.isValid(token)).isTrue();
        assertThat(jwt.getUserId(token)).isEqualTo(5L);
        assertThat(jwt.getRole(token)).isEqualTo("admin");
    }

    @Test
    void jetonSigneAvecUneAutreCleEstInvalide() {
        String token = new JwtUtil("a".repeat(32), 3600000).generate(5L, "admin", "EMPLOYE");

        assertThat(new JwtUtil("b".repeat(32), 3600000).isValid(token)).isFalse();
    }

    @Test
    void jetonExpireEstInvalide() {
        JwtUtil jwt = new JwtUtil("x".repeat(32), -1000);

        assertThat(jwt.isValid(jwt.generate(5L, "admin", "EMPLOYE"))).isFalse();
    }

    @Test
    void chaqueJetonAUnJtiUniqueLisible() {
        JwtUtil jwt = new JwtUtil("x".repeat(32), 3600000);

        String premier = jwt.generate(5L, "admin", "EMPLOYE");
        String second = jwt.generate(5L, "admin", "EMPLOYE");

        assertThat(jwt.getJti(premier)).isNotBlank().isNotEqualTo(jwt.getJti(second));
    }

    @Test
    void ladateDExpirationEstLisible() {
        JwtUtil jwt = new JwtUtil("x".repeat(32), 3600000);

        assertThat(jwt.getExpiration(jwt.generate(5L, "admin", "EMPLOYE")))
                .isAfter(java.time.Instant.now().plusSeconds(3000))
                .isBefore(java.time.Instant.now().plusSeconds(3700));
    }
}
