package com.kolekta;

import com.kolekta.entity.Acces;
import com.kolekta.entity.Employes;
import com.kolekta.repository.AccesRepository;
import com.kolekta.repository.EmployesRepository;
import com.kolekta.repository.ModePaiementRepository;
import com.kolekta.repository.RolesRepository;
import com.kolekta.repository.SexeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Base des tests d'intégration : une vraie base PostgreSQL 18 (même image que docker-compose),
 * schéma créé par Flyway, validé par Hibernate (ddl-auto=validate).
 *
 * Le conteneur est démarré une seule fois pour toute la suite de tests (champ statique) :
 * toutes les classes qui héritent de celle-ci partagent la même base. Les tests créent donc
 * leurs données avec des noms uniques et ne supposent jamais une base vide.
 * Aucune donnée du profil dev n'est chargée (seul le profil par défaut est actif).
 */
@SpringBootTest(properties = {
        // Faux secret réservé aux tests (au moins 32 caractères)
        "jwt.secret=secret-de-test-uniquement-pas-un-vrai-secret-0123456789",
        // Évite que le placeholder ${DB_PASSWORD} reste sans valeur ; le conteneur fournit les vraies infos
        "spring.datasource.password=inutilise"
})
public abstract class AbstractIntegrationTest {

    @ServiceConnection
    static final PostgreSQLContainer POSTGRES = new PostgreSQLContainer("postgres:18-alpine");

    static {
        POSTGRES.start();
    }

    @Autowired protected EmployesRepository employesRepository;
    @Autowired protected AccesRepository accesRepository;
    @Autowired protected RolesRepository rolesRepository;
    @Autowired protected SexeRepository sexeRepository;
    @Autowired protected ModePaiementRepository modePaiementRepository;
    @Autowired protected PasswordEncoder passwordEncoder;

    /** Crée un employé actif avec un accès (rôle : admin, gerant, rh ou collecteur). Retourne l'accès. */
    protected Acces creerEmploye(String nomUtilisateur, String motDePasse, String role) {
        Employes employe = new Employes();
        employe.setNom("Test");
        employe.setPrenoms("Employe");
        employe.setDtn(LocalDate.of(1990, 1, 1));
        employe.setSexe(sexeRepository.findById("A").orElseThrow());
        employe.setDateEmbauche(LocalDate.of(2020, 1, 1));
        employe.setSalaire(new BigDecimal("1000.00"));
        employe.setModePaiement(modePaiementRepository.findByLibelleIgnoreCase("Espèces").orElseThrow());
        employe = employesRepository.save(employe);

        Acces acces = new Acces();
        acces.setEmploye(employe);
        acces.setNomUtilisateur(nomUtilisateur);
        acces.setMotDePasse(passwordEncoder.encode(motDePasse));
        acces.setRole(rolesRepository.findByLibelle(role).orElseThrow());
        return accesRepository.save(acces);
    }

    /** Nom d'utilisateur unique : la base est partagée entre les tests. */
    protected static String loginUnique(String prefixe) {
        return prefixe + "-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
