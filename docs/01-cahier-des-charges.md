# Cahier des charges — Kolekta

*Document de cadrage projet. Rédigé comme il le serait pour un commanditaire
réel : c'est la référence stable à laquelle on revient si on doute du
périmètre. Le backlog (user stories, sprints) en est une traduction
opérationnelle qui, elle, change à chaque itération.*

---

## 1. Présentation du projet

### 1.1 Contexte
Madagascar, secteur de la distribution alimentaire. Les hôtels, restaurants
et particuliers qui veulent des produits agricoles frais (fruits, légumes)
de qualité et traçables font aujourd'hui face à un circuit d'approvisionnement
fragmenté : marchés, intermédiaires multiples, pas de garantie de régularité
ni de traçabilité.

### 1.2 Porteur de projet
Kolekta — société de distribution qui se positionne comme **intermédiaire
logistique intégré** entre les agriculteurs (fournisseurs) et les
professionnels/particuliers (clients).

### 1.3 Modèle métier (important, cadre tout le reste)
Kolekta n'est **pas une marketplace** qui met en relation agriculteurs et
acheteurs. Kolekta :
1. **Collecte** les produits chez les agriculteurs (fournisseurs), via un
   employé interne dédié (le "collecteur"), qui vérifie aussi la qualité
   sur place.
2. **Ne stocke pas longtemps** — les produits étant frais, le principe est
   un cycle court plutôt qu'un entrepôt permanent.
3. **Reçoit les commandes** des clients (hôtels, restaurants, particuliers),
   y compris sous forme d'**abonnement** (jour de livraison fixe, contenu du
   panier modifiable à chaque occurrence).
4. **Externalise la livraison** à un prestataire tiers — Kolekta ne possède
   pas de camions de livraison, ne gère ni carburant ni chauffeurs pour
   cette partie.
5. **Gère les paiements** dans plusieurs sens : ce qu'elle doit aux
   fournisseurs, ce que les clients lui doivent, et ce qu'elle doit au
   prestataire de livraison.

Le rôle "camion/livreur interne" du modèle initial est donc restreint à la
**collecte** (le collecteur va chercher les produits, avec son propre
moyen de transport ou un véhicule Kolekta léger). La livraison
proprement dite est un maillon externe.

#### Règles opérationnelles clés
- **Déclenchement de la collecte** : mixte — principalement une commande
  client confirmée, plus une marge de collecte anticipée pour les produits
  à forte rotation, décidée par une règle métier simple (v1) plutôt qu'un
  vrai système prédictif (réservé à une v2, une fois qu'il y a un
  historique de données réel à exploiter).
- **Zones et délais de livraison** : Antsirabe ville = délai court ; hors
  Antsirabe = délai indicatif de 2 jours, variable selon la distance. Une
  commande passée hors cycle attend la prochaine collecte planifiée puis
  est livrée dès que possible.
- **Point de rendez-vous** : pas de passage par un dépôt intermédiaire — le
  collecteur et le prestataire de livraison se synchronisent en cours de
  tournée pour le transfert direct des produits.
- **Conditions de paiement** : particuliers = règlement simple, sans
  option de crédit, sauf voucher s'ils sont abonnés (aucun remboursement
  du voucher : le solde restant à la résiliation, ou à la fin d'une pause
  d'un an ou après un an d'inactivité, est abandonné après avertissement explicite) ; B2B sans abonnement = règlement sous 10 jours après
  livraison ; B2B abonnés = paiement mensuel (facture groupée + plafond
  d'encours) et/ou voucher prépayé, combinables. Le règlement reste
  hors-ligne en v1 : l'app enregistre et suit, elle n'exécute aucun
  paiement.

---

## 2. Objectifs du projet

### 2.1 Objectifs métier
- Fiabiliser l'approvisionnement (savoir en temps réel ce qui est
  disponible chez quel fournisseur)
- Réduire le temps de traitement d'une commande, de la réception à la
  livraison
- Avoir une visibilité financière sur les deux flux de paiement
  (fournisseurs à payer / clients qui paient)
- Piloter les tournées de collecte et la coordination avec le prestataire
  de livraison sans conflit de planning
- Donner au dirigeant un tableau de bord pour piloter l'activité

### 2.2 Objectifs utilisateurs
- Un client (hôtel/resto/particulier) doit pouvoir commander en quelques
  clics et suivre sa livraison
- Un employé terrain doit savoir, sans ambiguïté, quoi collecter/livrer,
  où, et à quelle heure
- Le gérant doit pouvoir arbitrer (prioriser une commande, réassigner un
  collecteur) sans repasser par des tableurs

---

## 3. Périmètre du projet

### 3.1 Dans le périmètre
- Back-office de gestion : produits, catégories, fournisseurs et leurs
  disponibilités, employés et accès rôlés, véhicules et tournées de
  collecte, coordination avec le prestataire de livraison, commandes,
  abonnements, vouchers, paiements (clients, fournisseurs, prestataire,
  salaires) avec suivi des espèces, tâches internes, statistiques
- Espace client : catalogue, panier, commande, suivi de statut, historique,
  abonnement, voucher, référence mobile money
- Authentification par rôle (Admin, Gérant, RH, Collecteur, client)

### 3.2 Hors périmètre (explicitement exclu, à ne pas construire)
- Paiement en ligne intégré (carte bancaire, mobile money en direct dans
  l'app) — l'argent circule hors de l'app (espèces, mobile money) ; l'app
  enregistre le paiement, la référence de transaction et sa vérification
- Application mobile native pour le collecteur (web responsive en mode
  hors-ligne pour cette version)
- Gestion multi-entrepôt / multi-ville (un seul site de stockage supposé)
- Marketplace ouverte à d'autres distributeurs
- Accès délégué multi-utilisateur pour un compte B2B (un seul compte par
  établissement en v1 — décidé en phase backlog)

---

## 4. Utilisateurs et rôles

*(Détaillé en personas dans une phase dédiée — ici, juste la liste des
acteurs et ce qu'ils attendent du système en une phrase.)*

| Rôle | Ce qu'il attend du système |
|---|---|
| Admin | Vue complète, gestion des accès et des rôles |
| Gérant | Pilotage opérationnel : commandes, collecte, paiements, stats |
| RH | Gestion des employés, tâches, historique |
| Collecteur (interne) | Sa tournée de collecte du jour, les commandes à couvrir, le point de rendez-vous avec le prestataire de livraison |
| Prestataire de livraison (externe) | *Pas d'accès système en v1* — coordination par téléphone et SMS ; suivi de son paiement et des espèces qu'il détient dans le module Paiements |
| Client hôtel/resto | Commande récurrente ou abonnement, gros volumes, historique, facturation |
| Client particulier | Commande simple ou abonnement, suivi livraison |
| Fournisseur (agriculteur) | *Pas d'accès système dans cette version* — ses dispos sont saisies par le collecteur lors de la collecte |

---

## 5. Fonctionnalités attendues

*Priorisation MoSCoW : Must (indispensable), Should (important mais pas
bloquant), Could (si le temps le permet), Won't (explicitement écarté cette
version).*

| Module | Fonctionnalité | Priorité |
|---|---|---|
| Catalogue | CRUD produits, catégories | Must |
| Catalogue | Recherche/filtre produits | Should |
| Fournisseurs | CRUD fournisseurs | Must |
| Fournisseurs | Déclaration de disponibilité par un employé lors de la collecte | Must |
| Commandes | Panier, création commande, suivi de statut | Must |
| Commandes | Historique client | Should |
| Livraisons | Tournée de collecte (fournisseur → point de rendez-vous) | Must |
| Livraisons | Coordination avec le prestataire de livraison externe | Must |
| Livraisons | Zones et délais de livraison (ville vs hors-ville, selon distance) | Must |
| Livraisons | Planification des tournées et suivi de leur avancement par arrêt | Must |
| Commandes | Abonnement (jour fixe, panier modifiable à chaque occurrence) | Should |
| Commandes | Cycle de vie de l'abonnement : renouvellement automatique mensuel ou manuel (rappel J-5, livraisons arrêtées sans confirmation), pause (1 an maximum), résiliation avec avertissement sur le solde, fin automatique après 1 an d'inactivité | Should |
| Commandes | Spécification qualité/quantité par ligne de commande (gros comptes) | Should |
| Commandes | Remise permanente par compte pour les B2B à volume : le prix du catalogue reste identique pour tous, la remise s'y ajoute dans un plafond paramétrable | Should |
| Commandes | Remise du barème : bouton à la validation de commande pour les clients éligibles (B2B, ou particulier abonné depuis plus d'un mois) dont le total atteint le montant minimal | Must |
| Commandes | Faveur de remise accordée par le Gérant à un B2B qui la négocie par téléphone, avec motif | Should |
| Commandes | Annulation d'une commande avant sa collecte ; profil et adresses de livraison ; gestion des comptes clients | Must |
| Administration | Paramètres de l'application (plafond de remise, barème, seuils, délais, numéros mobile money de Kolekta) | Must |
| Commandes | Rapprochement commande/livraison à réception, signalement d'écart | Should |
| Collecte | Règle simple de collecte anticipée (volume additionnel sur produits à forte rotation) | Should |
| Collecte | Prévision prédictive réelle (historique, ML) | Won't (v1) — prévu v2 |
| Collecte | CRUD véhicules de collecte, assignation collecteur | Must |
| Paiements | Paiement commande (client → Kolekta) | Must |
| Paiements | Modes de paiement : espèces et mobile money (le client communique la référence de transaction (il la saisit lui-même ou la donne à un employé qui la saisit pour lui), le Gérant la vérifie avant de valider ; vaut aussi pour les recharges de voucher ; aucun paiement n'est exécuté dans l'app) | Must |
| Paiements | Suivi des espèces : un encaissement validé reste "détenu par l'employé" jusqu'à la remise confirmée par le Gérant à la caisse (écarts tracés) | Must |
| Paiements | Paiement fournisseur (Kolekta → fournisseur) : le plus souvent en espèces sur place par le collecteur avec une avance de caisse, ou en mobile money par le Gérant si le fournisseur le préfère | Must |
| Paiements | Prix d'achat par fournisseur et par produit (invisibles pour le collecteur) | Must |
| Paiements | Paiement du prestataire de livraison (remplace le paiement transport/camion) | Must |
| Paiements | Journal des paiements non modifiable (annulation plutôt que suppression) | Should |
| Paiements | Bon de commande et facture téléchargeables | Should |
| Paiements | Paiement mensuel B2B abonné : facture groupée de fin de mois + plafond d'encours par client | Should |
| Paiements | Voucher (crédit prépayé) pour tout compte abonné (B2B ou particulier), combinable avec le paiement mensuel pour les B2B | Should |
| Paiements | Délai de règlement de 10 jours pour les B2B sans abonnement | Must |
| Paiements | Salaires employés | Should |
| Employés | CRUD, accès rôlés | Must |
| Employés | Permissions strictes par rôle (collecteur sans accès aux paiements ni aux salaires, RH sans accès aux paiements clients/fournisseurs) | Must |
| Employés | Historique d'actions | Could |
| Tâches | Assignation, priorité, statut, historique | Must |
| Statistiques | Tableau de bord dirigeant (CA, commandes, collecte, finances, masse salariale) | Must |
| Statistiques | Alertes sur ce qui sort du cadre (retard collecte, paiement à échéance), pas juste des chiffres bruts | Should |
| Notifications | Email pour tous les événements (confirmation, statut, facture) | Must |
| Notifications | SMS "livraison en cours", si particulier ou pas d'email | Should |
| Paiements | Espèces remises au prestataire à la livraison : le client le déclare dans son espace, le prestataire est prévenu par SMS (contestation possible), puis remet l'argent à Kolekta (suivi des espèces détenues) | Must |
| Notifications | SMS au prestataire lors d'un paiement déclaré (rend la passerelle SMS indispensable en v1) | Must |

---

## 6. Contraintes

### 6.1 Techniques
- Backend existant à conserver et compléter : Spring Boot 3.2 / Java 17 /
  PostgreSQL / JWT
- Frontend à construire : une seule application Vue 3 + Tailwind CSS (espace
  client sous `/`, équipe sous `/admin`, accès contrôlés par rôle et côté API)
- Dépôt unique (monorepo) : `backend/`, `frontend/`, `docs/`, `docker-compose`
- Doit être déployable sur des services cloud standards (pas
  d'infrastructure propriétaire)
- Nécessite l'intégration d'une passerelle SMS tierce pour le SMS au
  prestataire (indispensable en v1) et la notification de livraison (ex :
  agrégateur type Twilio, ou opérateur local) — dépendance externe à
  documenter et à tester séparément

### 6.2 Organisationnelles
- Projet mené en solo (toi) avec Claude Code comme "développeur"
  d'exécution — donc chaque lot de travail doit être cadré assez
  précisément pour être confié tel quel
- Pas de budget/délai contractuel réel (projet portfolio), mais on se fixe
  des jalons pour s'entraîner à en tenir

---

## 7. Livrables attendus

1. Brand kit (logo, palette, typographie)
2. Cahier des charges — ce document
3. Personas
4. Backlog de user stories priorisées
5. Cartographie écrans → actions → user stories → endpoints API
6. Wireframes des écrans principaux
7. Plan de sprints (S0 à S10)
8. Backend complété (modules manquants identifiés)
9. Frontend Vue fonctionnel
10. Déploiement en ligne (démo accessible par lien) et README de portfolio

---

## 8. Critères de recette (définition de "fini")

Une fonctionnalité est considérée terminée quand :
- L'endpoint API existe, est sécurisé par le bon rôle, et retourne les
  bons statuts HTTP
- L'écran correspondant existe côté frontend et appelle réellement l'API
  (pas de mock)
- Le cas d'erreur principal est géré (ex : commande sur produit en
  rupture, véhicule de collecte déjà assigné)
- Des tests automatiques couvrent la règle métier et l'endpoint, et passent en CI
- Testé manuellement de bout en bout sur le parcours nominal

---

## 9. Gouvernance du projet

- Fonctionnement agile allégé : backlog priorisé, découpé en petits lots
  ("sprints" informels sans durée fixe, S0 à S10)
- Toi = product owner (tu arbitres les priorités et valides chaque lot)
- Moi = accompagnement cadrage/design/spécification
- Claude Code = exécution technique, sur la base de tickets précis dérivés
  de ce document et du backlog
- Un lot livré = une revue avant de passer au suivant (pas d'enchaînement
  sans validation)
