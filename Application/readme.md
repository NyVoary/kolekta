# MVC spring-boot par défaut
lancé le projet:
# mvn spring-boot:run 

Technologies utilisées:

Spring Boot : Framework backend Java pour créer des API REST rapidement.
PostgreSQL : Système de gestion de base de données relationnelle.
Maven : Outil de gestion de projet et de dépendances.
Spring Data JPA : Pour la communication avec la base de données.

Architecture du projet
Frontend (jsp)
            ↓ (Requêtes HTTP)
Backend (API REST - Spring Boot)
            ↓ (JPA/Hibernate)
Base de données (PostgreSQL)

Modules du projet
Interface utilisateurs:
        -commande
        -gestion de profil
        -panier
        -liste produit
Authentification:
        -login/sign up
        -validation commande
        -validation paiement
Gestion des utilisateurs:
        -liste des utilisateurs
        -details de chaque utilisateur:
                -commandes passés
                -
Gestion des produits:
        -statistiques
CRUD des entités
Espace administrateur