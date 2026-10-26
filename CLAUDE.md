# Kolekta — guide pour Claude Code

Lis ce fichier au début de chaque session. Il résume le projet, les décisions déjà prises
et les règles de travail. En cas de doute sur le périmètre, `docs/` fait foi.

## Le projet
Kolekta est une plateforme de distribution de produits agricoles frais (Antsirabe, Madagascar),
projet de portfolio de Ny Voary (étudiante, seule sur le projet). Kolekta n'est PAS une marketplace :
un collecteur interne va chercher les produits chez les agriculteurs, le stock est minimal, la
livraison est confiée à un prestataire externe (rendez-vous en route, pas de dépôt).
Les agriculteurs et le prestataire n'ont pas d'accès au système.
Origine : projet de groupe 2025 (cours de gestion de projet), refonte REST en solo en 2026.
L'ancien code est conservé sous le tag Git `legacy-avant-kolekta`.

## Stack
- Backend : Java 25, Spring Boot 4.1, Spring Security + JWT, Spring Data JPA / Hibernate 7,
  PostgreSQL 18, Flyway, Lombok. Dossier `backend/`, package `com.kolekta`. Pas de MapStruct.
- Frontend : UNE seule application Vue 3 + TypeScript + Vite + Tailwind + Pinia + Vue Router.
  Espace client sous `/`, espace équipe sous `/admin`. Dossier `frontend/`.
- Lancement : `docker compose up --build` (projet compose "kolekta"), configuration par variables
  d'environnement (voir `.env.example`). Profil Spring `dev` = données de démo fictives.
- Documents : `docs/` (voir `docs/README.md`) : `01-cahier-des-charges.md`, `02-personas.html`,
  `03-brand-kit.html`, `04-backlog.html`, `05-cartographie-ecrans.html`, `06-plan-sprints.html`,
  `methode-projet.md`.
- Variables d'environnement (`.env.example`) : `DB_NAME`, `DB_USER`, `DB_PASSWORD`, `DB_URL` (hors
  Docker seulement), `DB_PORT` (5433 par défaut), `JWT_SECRET` (obligatoire), `JWT_EXPIRATION`
  (3600000 ms), `CORS_ALLOWED_ORIGINS` (origines autorisées, `http://localhost:5173` par défaut),
  `FRONTEND_PORT` (3000). Le frontend est servi sur `localhost:3000`, l'API sur `8080`.

## Règles de travail (obligatoires)
1. Un ticket = une branche (`feat/...`, `chore/...`) = une pull request. Commits par thème, atomiques.
2. Ne JAMAIS pousser ni réécrire l'historique sans demande explicite. Donne la commande à lancer.
3. Avant de commencer : `git status`. Du travail non commité → arrête-toi et demande.
4. Explique chaque changement en français, simplement : Ny Voary apprend.
5. Ne modifie JAMAIS une migration Flyway déjà fusionnée dans `main` : ajoute une nouvelle migration.
6. Aucun secret dans le dépôt. Données de démo fictives uniquement (e-mails en @example.com).
7. Chaque ticket livre : migration + entités + logique métier + endpoint sécurisé par rôle +
   écran + tests automatiques (service et endpoint). Ne livre pas un endpoint sans l'écran, ni l'inverse.
8. Pas de dépendance ajoutée sans raison claire. Vérifie les versions sur les sources officielles.
9. Ne touche pas à ce qui n'est pas dans le ticket. Si tu découvres un manque, signale-le.
10. Un écran ou un bouton doit descendre d'une user story (voir `docs/`) ; sinon signale-le.
11. Tout nouvel endpoint doit être déclaré dans `SecurityConfig` avec ses rôles, et testé en 200 et en 403.
    Sinon il est fermé par défaut (`denyAll`), même pour l'Admin ; `RouteDeclarationsTest` signale un oubli.

## Décisions métier verrouillées
- Rôles : Admin, Gérant, RH, Collecteur (employés) + client (B2B ou particulier). Contrôle côté API
  (403), pas seulement côté écran. Un seul compte par établissement B2B.
- Le catalogue est public ; le prix du catalogue est IDENTIQUE pour tous. Les remises s'ajoutent :
  barème automatique à la demande (B2B, ou particulier abonné depuis plus d'un mois, total minimal),
  remise permanente par compte (B2B), faveur accordée par le Gérant, le tout limité par un plafond.
- Les paiements sont ENREGISTRÉS, jamais exécutés dans l'app (espèces, mobile money). Référence
  mobile money saisie par le client ou un employé, vérifiée par le Gérant. Journal des paiements
  non modifiable (annulation, jamais suppression). Aucun remboursement.
- Espèces : un encaissement validé reste « détenu par » l'employé ou le prestataire jusqu'à la
  remise confirmée par le Gérant. Le voucher est un crédit prépayé (réservation à la commande,
  débit final sur les quantités livrées).
- Disponibilité d'un produit : calculée, déclaration fournisseur de moins de 7 jours. Fournisseurs
  payés sur place (avance de caisse du collecteur) ou en mobile money. Prix d'achat invisibles du collecteur.
- Collecte : confirmations de commande + règle simple de +15 % (modifiable). Prévision par ML = v2.
  Le collecteur travaille hors-ligne (synchronisation différée). SMS : passerelle nécessaire en v1.
- Hors périmètre v1 : paiement en ligne intégré, application mobile native, multi-entrepôt,
  comptes B2B à plusieurs utilisateurs.
- Une connexion compte comme une activité d'abonnement.

## Conventions de code
- Suivre les conventions existantes du dépôt avant d'en inventer. Domaine nommé en français (tables
  et entités existantes), termes techniques en anglais.
- Backend : DTO request/response séparés des entités (jamais d'entité exposée), service = interface +
  implémentation, exceptions via les classes existantes, nouvelles routes déclarées dans la config de
  sécurité avec les bons rôles. Clés primaires BIGINT identity. `ddl-auto=validate`.
- Frontend : Composition API, `<script setup lang="ts">`, un fichier d'API et un store par domaine,
  charte graphique via les tokens Tailwind (vert #2F7D46, forêt #1E5A31, terracotta #D97B4F,
  ocre #E3A857, crème #F3EEE1, encre #16281D ; titres Manrope, texte Inter), contraste 4,5:1.
  États à prévoir sur chaque écran : chargement, succès, erreur, vide.

## Avancement
Sprints S0 à S10 : voir `docs/06-plan-sprints.html`. S0 (socle) est terminé et fusionné dans `main`.
S1-1 (connexion, déconnexion, redirection par rôle : US-001, US-003, US-082) est terminé et fusionné.
Ticket `chore/security-deny-by-default` en cours de PR : `SecurityConfig` liste chaque route avec ses rôles
et se termine par `anyRequest().denyAll()` (un endpoint oublié est fermé, 403 ; Swagger n'est ouvert que
si `springdoc.*.enabled=true`, donc en profil dev). Prochain : la suite de S1.
Noyau existant : authentification, employés, clients (module « Accès » seulement ; les autres
modules seront reconstruits).
Connexion (S1-1) : un seul `POST /api/auth/login` (nom d'utilisateur pour un employé, e-mail pour un
client ; le téléphone n'est pas géré car non unique en base). Réponse : jeton, rôle, identité. Identifiants
invalides = 401 générique ; compte désactivé = 403 avec `code=COMPTE_DESACTIVE` (message « contactez
Kolekta » pour un client). `GET /api/auth/me` (refuse aussi un compte devenu inactif) et
`POST /api/auth/logout` : chaque jeton a un `jti` ; la déconnexion l'écrit dans `jetons_revoques` (V3) et
`JwtAuthFilter` refuse (401) un jeton révoqué ; nettoyage horaire des lignes expirées. Frontend : page
`/connexion`, redirection admin/gerant -> `/admin`, rh -> `/admin/employes`, collecteur ->
`/admin/tournee`, client -> `/` ; session restaurée au démarrage par `/me` (401 -> `/connexion?expiree=1`).
Socle API (S0-8) : erreurs au format RFC 9457 (`ProblemDetail`) via `GlobalExceptionHandler` et
`RestSecurityErrorHandlers` (jamais de trace ni de message interne côté client) ; 401 sans jeton ou
avec jeton invalide, 403 si le rôle ne suffit pas ; `JWT_SECRET` de 32 octets minimum (sinon l'appli
ne démarre pas) ; CORS via `CORS_ALLOWED_ORIGINS` ; `open-in-view=false` : toute lecture de relation
LAZY doit se faire dans un service `@Transactional(readOnly = true)`. Swagger UI sur
`/swagger-ui.html`, profil `dev` seulement.
Tests backend : `backend/src/test` (classe de base `AbstractIntegrationTest` : PostgreSQL 18 via
Testcontainers, Docker requis ; tests nommés `*Test`, lancés par `./mvnw verify`). CI :
`.github/workflows/ci.yml` (jobs backend et frontend) ; Dependabot hebdomadaire, mises à jour
mineures groupées. Mets cette section à jour à la fin de chaque ticket.
