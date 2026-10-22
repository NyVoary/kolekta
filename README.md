# Kolekta

API REST de gestion pour une entreprise de distribution de produits agricoles malgaches : fournisseurs, produits, commandes, paniers, livraisons (camions) et tâches internes. Un frontend Vue est prévu.

## Stack

| Couche | Technologie |
| --- | --- |
| Langage | Java 25 |
| Framework | Spring Boot 4.1 (Spring MVC, Spring Data JPA, Spring Security) |
| Base de données | PostgreSQL 18, migrations versionnées avec Flyway |
| Authentification | JWT |
| Boilerplate | Lombok |
| Validation | Bean Validation |
| Build | Maven (wrapper inclus) |
| Conteneurs | Docker, Docker Compose |
| Frontend | Vue (à venir) |

## Structure

```text
.
├── backend/                         # API Spring Boot
│   ├── Dockerfile
│   └── src/main/resources/
│       ├── application.properties   # configuration lue depuis l'environnement
│       ├── application-dev.properties
│       └── db/
│           ├── migration/           # V1 schéma, V2 données de référence (Flyway)
│           └── dev-seed/            # données de démonstration fictives (profil dev)
├── docker-compose.yml               # PostgreSQL + API
├── .env.example                     # modèle de configuration (à copier en .env)
└── README.md
```

## Lancer le projet

### Avec Docker (recommandé)

Prérequis : Docker et Docker Compose.

```bash
# 1. Créer ta configuration locale, puis remplacer DB_PASSWORD et JWT_SECRET
cp .env.example .env

# 2. Construire et démarrer PostgreSQL + l'API
docker compose up --build
```

L'API est disponible sur `http://localhost:8080`. Au premier démarrage, Flyway crée le schéma, insère les données de référence, puis (profil `dev`, activé par le compose) les données de démonstration.

Pour tout arrêter : `docker compose down` (ajouter `-v` pour supprimer aussi la base).

Le fichier `.env` n'est jamais versionné. `JWT_SECRET` est obligatoire (32 caractères minimum) : l'application refuse de démarrer sans lui. Pour en générer un : `openssl rand -base64 48`.

### Sans Docker

Prérequis : JDK 25 et un PostgreSQL local.

```bash
# 1. Créer une base vide (Flyway créera les tables au démarrage)
createdb -U postgres kolekta

# 2. Configurer : copier .env.example en .env et adapter DB_URL, DB_USER, DB_PASSWORD, JWT_SECRET
cp .env.example .env

# 3. Lancer l'API (profil dev pour charger aussi les données de démonstration)
cd backend
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

L'application lit `../.env` automatiquement. Sans le profil `dev`, seules les données de référence sont créées.

## Comptes de démonstration

Ces comptes n'existent qu'avec le profil `dev`. Ils sont fictifs et ne doivent jamais être utilisés en production.

| Type | Identifiant | Mot de passe | Rôle |
| --- | --- | --- | --- |
| Employé | `admin.demo` | `Admin-Demo-2026` | admin |
| Employé | `gerant.demo` | `Gerant-Demo-2026` | gerant |
| Employé | `rh.demo` | `Rh-Demo-2026` | rh |
| Employé | `livreur.demo` | `Livreur-Demo-2026` | livreur |
| Client | `alice.demo@example.com` | `Client-Demo-2026` | client |
| Client | `bob.demo@example.com` | `Client-Demo-2026` | client |

Connexion :

```bash
# Client
curl -X POST http://localhost:8080/api/auth/client/login \
  -H "Content-Type: application/json" \
  -d '{"login":"alice.demo@example.com","motDePasse":"Client-Demo-2026"}'

# Employé
curl -X POST http://localhost:8080/api/auth/employe/login \
  -H "Content-Type: application/json" \
  -d '{"login":"admin.demo","motDePasse":"Admin-Demo-2026"}'
```

La réponse contient un `token` à passer ensuite dans l'en-tête `Authorization: Bearer <token>`.

## Base de données

Le schéma est géré par Flyway (`backend/src/main/resources/db/migration`). Hibernate est configuré en `ddl-auto=validate` : il vérifie le schéma mais ne le modifie jamais. Toute évolution passe par une nouvelle migration `V3__…`, `V4__…`.
