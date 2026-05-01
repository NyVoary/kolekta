# Lokaly

Plateforme de gestion pour une entreprise de distribution de produits agricoles malgaches. Elle couvre l'ensemble de la chaîne opérationnelle : gestion des fournisseurs et des stocks, traitement des commandes clients, suivi des livraisons, et supervision des employés.

## Fonctionnalités principales

- **Catalogue produits** — fruits et légumes par catégorie, avec disponibilités fournisseur
- **Commandes clients** — panier, validation, suivi de statut (en attente → livrée)
- **Livraisons** — assignation de camions, traçage des tournées (collecte / livraison)
- **Paiements** — commandes, fournisseurs et transport, avec modes multiples (virement, espèces, mobile money…)
- **Gestion des employés** — fiches, accès rôlés (admin / employé), historique, salaires
- **Tâches** — assignation, priorités, suivi de statut pour l'équipe interne
- **Statistiques** — tableaux de bord pour le directeur

## Stack technique

| Couche | Technologie |
| --- | --- |
| Langage | Java 17 |
| Framework backend | Spring Boot 3.2 (Spring MVC, Spring Data JPA) |
| Base de données | PostgreSQL |
| ORM | Hibernate (via JPA) |
| Mapping DTO | MapStruct |
| Réduction du boilerplate | Lombok |
| Validation | Spring Validation (Bean Validation 3) |
| Vues serveur | JSP (Tomcat Embed Jasper) |
| Frontend | HTML / CSS / JavaScript (template Skydash) |
| Build | Maven |

## Structure du projet

```text
Lokaly/
├── Application/          # Code source Spring Boot
│   └── src/
│       └── main/
│           ├── java/     # Controllers, Services, Repositories, Entities, DTOs
│           └── resources/
│               ├── static/      # CSS, JS, images (produits & fournisseurs)
│               └── templates/   # Page d'accueil HTML
├── db/
│   ├── schema.sql        # Schéma complet de la base de données
│   └── data.sql          # Données de référence et seed data
└── README.md
```

## Mise en place

**Prérequis** : Java 17, Maven, PostgreSQL

```bash
# 1. Créer la base de données
psql -U postgres -f db/schema.sql
psql -U postgres -d agro2 -f db/data.sql

# 2. Configurer la connexion
# Copier et remplir Application/src/main/resources/application.properties
# (spring.datasource.url, username, password)

# 3. Lancer l'application
cd Application
mvn spring-boot:run
```

L'application est accessible sur `http://localhost:8080`.
