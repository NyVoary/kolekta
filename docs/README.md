# Documentation de Kolekta

Ces documents décrivent le projet avant le code. En cas de doute sur le périmètre, ils font foi
(voir aussi [CLAUDE.md](../CLAUDE.md) à la racine). Les fichiers sont numérotés dans l'ordre où on
les lit.

| Document | À quoi il sert |
| --- | --- |
| [01-cahier-des-charges.md](01-cahier-des-charges.md) | Le besoin : ce que fait Kolekta, pour qui, et ce qui est hors périmètre de la v1. Point de départ de tout le reste. |
| [02-personas.html](02-personas.html) | Les personnes qui utilisent (ou subissent) le système : clients, employés. Sert à vérifier qu'un écran répond à un vrai besoin. |
| [03-brand-kit.html](03-brand-kit.html) | L'identité visuelle : logo, couleurs, typographies, ton. Les tokens Tailwind du frontend en sont issus. |
| [04-backlog.html](04-backlog.html) | Les epics et les user stories (US-xxx) avec leurs critères d'acceptation. Chaque écran et chaque bouton doit descendre d'une story d'ici. |
| [05-cartographie-ecrans.html](05-cartographie-ecrans.html) | Pour chaque écran : ses éléments, l'action derrière chacun, la story qui la justifie et l'endpoint appelé. |
| [06-plan-sprints.html](06-plan-sprints.html) | Le découpage en sprints (S0 à S10) et en tickets, dans l'ordre de réalisation. |
| [methode-projet.md](methode-projet.md) | La méthode de travail (avant / pendant / après) appliquée à chaque projet. |

## Quel document ouvrir ?

- Je veux comprendre le projet : `01`, puis `02`.
- Je dois faire un écran ou un bouton : `05` pour sa place, `04` pour la story qui le justifie.
- Je dois choisir une couleur ou une police : `03`.
- Je veux savoir quel est le prochain ticket : `06`.
