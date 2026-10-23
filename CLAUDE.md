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
  (3600000 ms), `FRONTEND_PORT` (3000). Le frontend est servi sur `localhost:3000`, l'API sur `8080`.

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
Sprints S0 à S10 : voir `docs/06-plan-sprints.html`. S0 (socle), fusionné dans `main` : S0-1 à S0-5
(dépôt nettoyé, package `com.kolekta`, versions à jour, Flyway + Docker, squelette frontend).
Noyau existant : authentification, employés, clients (module « Accès » seulement ; les autres
modules seront reconstruits). Comportement actuel de l'API : sans jeton ou sans le bon rôle → 403
(le 401 est prévu en S0-8) ; la longueur minimale du secret JWT n'est pas vérifiée par le code ;
il n'y a pas encore de tests backend (`src/test` absent) ni de CI.
Reste en S0 : S0-6 docs, CLAUDE.md et README (branche `chore/s0-6-docs`), S0-7 CI GitHub Actions +
base de tests (Testcontainers), S0-8 socle API (Swagger, erreurs JSON cohérentes, revue de sécurité,
401 au lieu de 403 sans jeton, longueur du secret JWT, CORS pour le front). Mets cette section à jour
à la fin de chaque ticket.
