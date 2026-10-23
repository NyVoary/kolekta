# Méthode projet — Template avant / pendant / après

*Template générique de gestion de projet, réutilisable pour n'importe quel
projet futur — pas spécifique à un projet en particulier. Les exemples
concrets viennent du projet Kolekta, mais uniquement pour illustrer
comment remplir le template ; le template lui-même est neutre.*

---

## A. AVANT DE CODER — Discovery & Design

Checklist dans l'ordre, chaque étape dépend de la précédente.

| # | Étape | Ce que ça produit | Sert à |
|---|---|---|---|
| 1 | **Cahier des charges** | Contexte, objectifs, périmètre (in/out), contraintes, livrables, critères de recette | Cadrer ce qu'on construit et pour qui, avant tout |
| 2 | **Brand kit** | Logo, palette, typographie, ton | Identité visuelle cohérente dès le premier écran |
| 3 | **Personas** | Fiches profils (contexte, objectifs, frustrations, scénario clé) | Ancrer chaque décision produit sur un utilisateur réel, pas abstrait |
| 4 | **Backlog en user stories** | Epics → stories au format "en tant que / je veux / afin de" + critères Given/When/Then | Traduire le besoin en unités développables et testables |
| 5 | **Architecture de l'info** | Pour chaque écran : liste des actions/boutons, et pour chacun la story + l'endpoint API correspondant | Vérifier qu'aucun bouton n'existe sans action réelle derrière |
| 6 | **Wireframes** | Maquettes basse/moyenne fidélité des écrans principaux | Valider la disposition avant de coder l'UI |
| 7 | **Sprints** | Stories regroupées en petits lots livrables, priorisés | Découper le travail en morceaux qu'on peut confier un par un |
| 8 | **Setup technique** | Structure du repo, environnement (docker-compose, .env), CI basique si besoin | Un socle propre avant la première feature |

**Règle** : on ne code rien tant que l'étape 5 n'a pas explicitement rattaché
la fonctionnalité à une story du backlog. Si un écran ou un bouton ne
descend d'aucune story, soit il ne sert à rien, soit une story manque.

---

## B. LE TEMPLATE PAR FONCTIONNALITÉ (à remplir pour chaque ticket)

C'est ici que le triptyque **écran / base / métier** intervient — une
fonctionnalité n'est complète que quand les trois couches existent et se
parlent.

```
### Ticket — [Nom de la fonctionnalité]

User story liée : US-XXX
Epic : [nom de l'epic]
Priorité (MoSCoW) : Must / Should / Could

──────────────────────────────
1. BASE (données)
──────────────────────────────
- Entité(s) concernée(s) :
- Nouvelle table / migration nécessaire ? oui / non
- Relations avec des entités existantes :
- Contraintes (unicité, valeurs par défaut, nullabilité) :

──────────────────────────────
2. MÉTIER (règles, logique)
──────────────────────────────
- Service(s) à créer ou modifier :
- Règles métier à respecter (ex : un produit non conforme n'est pas
  ajouté au lot collecté) :
- Cas d'erreur à gérer explicitement :
- Rôles autorisés à déclencher cette action :

──────────────────────────────
3. ÉCRAN (interface)
──────────────────────────────
- Route / vue concernée :
- Composants nécessaires (nouveaux ou réutilisés) :
- Pour chaque bouton/action de l'écran → quel endpoint il appelle :
- États à prévoir : chargement, succès, erreur, vide (aucune donnée)

──────────────────────────────
DEFINITION OF DONE (reprend les critères Given/When/Then de la story)
──────────────────────────────
- [ ] Endpoint créé, sécurisé par le bon rôle, testé manuellement
- [ ] Tests automatiques écrits pour la règle métier et l'endpoint (passent en CI)
- [ ] Écran connecté à l'API réelle (pas de données simulées)
- [ ] Cas d'erreur principal géré et visible pour l'utilisateur
- [ ] Testé de bout en bout sur le parcours nominal
- [ ] N'a rien cassé dans les fonctionnalités déjà livrées
```

### Exemple rempli, pour illustration (issu du projet Kolekta)

*Ceci n'est pas une partie du template — c'est un exemple de remplissage,
pour montrer à quoi ça ressemble une fois complété sur un cas réel.*

```
### Ticket — Gestion des catégories de produits

User story liée : (à créer dans l'epic Catalogue)
Epic : Catalogue
Priorité : Must

1. BASE
- Entité : Categories (existe déjà en base, jamais utilisée par un service)
- Migration nécessaire ? Non
- Relations : Produits → Categories (déjà en place au niveau entité)
- Contraintes : nom de catégorie unique

2. MÉTIER
- Service à créer : CategorieService (CRUD)
- Règles : on ne peut pas supprimer une catégorie encore utilisée par
  des produits actifs
- Cas d'erreur : nom déjà existant, suppression bloquée si produits liés
- Rôles autorisés : Admin, Gérant

3. ÉCRAN
- Route : /admin/categories
- Composants : liste des catégories, formulaire création/édition, modale
  de confirmation de suppression
- Boutons → endpoints : "Ajouter" → POST /api/categories,
  "Modifier" → PUT /api/categories/{id},
  "Supprimer" → DELETE /api/categories/{id}
- États : liste vide (aucune catégorie), erreur de suppression bloquée
```

---

## C. PENDANT LE CODE — Discipline à tenir

- **Un ticket = une story ou un petit lot cohérent**, jamais un epic entier
  d'un coup
- **Relire chaque diff** avant de l'accepter — jamais valider un lot sans
  l'avoir compris
- **Tester manuellement le critère d'acceptation** (Given/When/Then) avant
  de considérer une story faite
- **Commits atomiques**, message clair et descriptif du changement réel
- **Ne jamais casser l'existant** — un module qui marchait doit encore
  marcher après le lot suivant
- **Backlog vivant** : cocher ce qui est fait, ajouter ce qu'on découvre
  en cours de route (une règle découverte pendant la cartographie
  devient une story ou un critère ajouté)
- **Petites démos régulières**, même seul — ouvrir l'app, vérifier que ça
  correspond vraiment à l'intention, pas juste "ça compile"

---

## D. APRÈS LE CODE

| Étape | Contenu |
|---|---|
| **Recette** | Vérifier chaque module contre les critères de recette du CDC (section 8), pas juste "ça a l'air de marcher" |
| **Déploiement** | Backend et frontend déployés selon les contraintes techniques définies dans le CDC du projet (conteneurisation, service cloud, environnement de démo local reproductible) |
| **Documentation** | README à jour, captures d'écran, éventuellement une courte démo — ce qui rend le projet présentable en portfolio |
| **Rétro** | Qu'est-ce qui a bien marché dans la méthode, qu'est-ce qu'on changerait — même en solo, ce réflexe vaut la peine |
| **Roadmap v2** | Tout ce qui a été mis en `Won't` ou `Could` non fait devient la base d'une v2 documentée, pas une case oubliée |
