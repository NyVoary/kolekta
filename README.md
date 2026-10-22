# Kolekta

API REST de gestion pour une entreprise de distribution de produits agricoles malgaches : fournisseurs, produits, commandes, paniers, livraisons (camions) et tâches internes. Un frontend Vue est prévu.

## Stack

| Couche | Technologie |
| --- | --- |
| Langage | Java 17 |
| Framework | Spring Boot 3.2 (Spring MVC, Spring Data JPA) |
| Base de données | PostgreSQL |
| Authentification | JWT |
| Mapping / boilerplate | MapStruct, Lombok |
| Validation | Bean Validation |
| Build | Maven (wrapper inclus) |
| Frontend | Vue (à venir) |

## Structure

```text
.
├── backend/   # API Spring Boot (controllers, services, repositories, entities, DTOs)
├── db/        # Schéma SQL et données de référence
└── README.md
```

## Démarrage

Prérequis : Java 17, PostgreSQL.

```bash
# 1. Base de données
psql -U postgres -f db/schema.sql

# 2. Configuration : copier puis renseigner la connexion et le secret JWT
cd backend
cp src/main/resources/application-example.properties src/main/resources/application.properties

# 3. Lancer l'API
./mvnw spring-boot:run
```

L'API est disponible sur `http://localhost:8080`.
