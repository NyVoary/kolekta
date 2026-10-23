package com.kolekta;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Garde-fou : les tests ne doivent dépendre ni des variables d'environnement, ni du fichier .env
 * de la machine (cause de l'échec de la CI sur JWT_SECRET alors que tout passait en local).
 *
 * Règle : toute propriété de src/main/resources/application.properties dont la valeur vient de
 * l'environnement (écrite ${...}) doit être définie dans kolekta-test.properties, ET c'est bien cette
 * valeur que l'application des tests utilise. Si on ajoute demain une propriété ${NOUVELLE_VARIABLE},
 * ce test échoue tant qu'on ne la déclare pas dans kolekta-test.properties.
 */
class TestIsolationTest extends AbstractIntegrationTest {

    /** Fournies par le conteneur PostgreSQL (@ServiceConnection) : inutile de les définir. */
    private static final Set<String> FOURNIES_PAR_LE_CONTENEUR =
            Set.of("spring.datasource.url", "spring.datasource.username");

    @Autowired
    ConfigurableEnvironment environment;

    private static Properties charger(String chemin) throws IOException {
        Properties props = new Properties();
        try (InputStream in = new ClassPathResource(chemin).getInputStream()) {
            props.load(in);
        }
        return props;
    }

    private static Set<String> proprietesVenantDeLEnvironnement(Properties principal) {
        Set<String> cles = new TreeSet<>();
        for (String cle : principal.stringPropertyNames()) {
            if (principal.getProperty(cle).contains("${") && !FOURNIES_PAR_LE_CONTENEUR.contains(cle)) {
                cles.add(cle);
            }
        }
        return cles;
    }

    @Test
    void toutesLesProprietesVenantDeLEnvironnementSontDefiniesParLesTests() throws IOException {
        Properties principal = charger("application.properties");
        Properties tests = charger("kolekta-test.properties");

        Set<String> aDefinir = proprietesVenantDeLEnvironnement(principal);
        assertThat(aDefinir).as("sanity check : application.properties contient bien des ${...}").isNotEmpty();

        assertThat(tests.stringPropertyNames())
                .as("à ajouter dans src/test/resources/kolekta-test.properties")
                .containsAll(aDefinir);
    }

    @Test
    void lesValeursUtiliseesViennentDuFichierDeTestEtPasDeLaMachine() throws IOException {
        for (String cle : proprietesVenantDeLEnvironnement(charger("application.properties"))) {
            PropertySource<?> source = environment.getPropertySources().stream()
                    // « configurationProperties » est une source virtuelle qui enveloppe toutes les autres
                    .filter(s -> !s.getName().equals("configurationProperties"))
                    .filter(s -> s.containsProperty(cle))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("propriété absente : " + cle));

            assertThat(source.getName())
                    .as("la propriété « %s » doit venir de kolekta-test.properties, pas de l'environnement "
                            + "ni du .env (source trouvée : %s)", cle, source.getName())
                    .contains("kolekta-test.properties");
        }
    }
}
