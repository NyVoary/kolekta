package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "commande_produits")
public class CommandeProduit {
<<<<<<< HEAD
=======

>>>>>>> origin/sarobidy-admin
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commande_produit")
    private Long id;

<<<<<<< HEAD
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_commande", nullable = false)
    private Commandes commande;

    @ManyToOne(fetch = FetchType.LAZY)
=======
    @ManyToOne
    @JoinColumn(name = "id_commande", nullable = false)
    private Commande commande;

    @ManyToOne
>>>>>>> origin/sarobidy-admin
    @JoinColumn(name = "id_produit", nullable = false)
    private Produits produit;

    @Column(nullable = false)
    private Integer quantite;

    @Column(name = "prix_unitaire", nullable = false)
<<<<<<< HEAD
    private Double prixUnitaire;

    // Getters and Setters
=======
    private double prixUnitaire;

    // Constructeurs
    public CommandeProduit() {
    }

    public CommandeProduit(Commande commande, Produits produit, Integer quantite, double prixUnitaire) {
        this.commande = commande;
        this.produit = produit;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
    }

    // Getters et Setters
>>>>>>> origin/sarobidy-admin
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

<<<<<<< HEAD
    public Commandes getCommande() {
        return commande;
    }

    public void setCommande(Commandes commande) {
=======
    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
>>>>>>> origin/sarobidy-admin
        this.commande = commande;
    }

    public Produits getProduit() {
        return produit;
    }

    public void setProduit(Produits produit) {
        this.produit = produit;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

<<<<<<< HEAD
    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Double getSousTotal() {
        return this.prixUnitaire * this.quantite;
    }
}
=======
    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    // Méthode utilitaire pour calculer le montant total de la ligne
    public double getMontantTotal() {
        return quantite * prixUnitaire;
    }
}
>>>>>>> origin/sarobidy-admin
