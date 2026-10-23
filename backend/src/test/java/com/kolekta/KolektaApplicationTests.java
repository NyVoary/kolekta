package com.kolekta;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Si ce test passe : les migrations Flyway s'appliquent sur une base vide ET Hibernate
 * (ddl-auto=validate) trouve un schéma conforme aux entités.
 */
class KolektaApplicationTests extends AbstractIntegrationTest {

    @Autowired
    JdbcTemplate jdbc;

    @Test
    void contexteDemarreEtMigrationsAppliquees() {
        // Les données de référence (V2) sont là ; les données de démo (V900, profil dev) ne le sont pas.
        Integer roles = jdbc.queryForObject("SELECT count(*) FROM roles", Integer.class);
        Integer demo = jdbc.queryForObject(
                "SELECT count(*) FROM acces WHERE nom_utilisateur = 'admin.demo'", Integer.class);

        assertThat(roles).isEqualTo(4);
        assertThat(demo).isZero();
    }
}
