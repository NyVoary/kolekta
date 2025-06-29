package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "commande_produits")
public class CommandeProduit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commande_produit")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_commande", nullable = false)
    private Commande commande;

    @ManyToOne
    @JoinColumn(name = "id_produit", nullable = false)
    private Produit produit;

    @Column(name = "quantite", nullable = false)
    private int quantite;

    @Column(name = "prix_unitaire", nullable = false)
    private Double prixUnitaire;

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
}
