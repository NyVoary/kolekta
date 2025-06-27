package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "commande_produits")
public class CommandeProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCommandeProduit;

    @ManyToOne
    @JoinColumn(name = "id_commande", nullable = false)
    private Commande commande;

    @ManyToOne
    @JoinColumn(name = "id_produit", nullable = false)
    private Produit produit;

    private Integer quantite;
    private BigDecimal prixUnitaire;
    public Long getIdCommandeProduit() {
        return idCommandeProduit;
    }
    public void setIdCommandeProduit(Long idCommandeProduit) {
        this.idCommandeProduit = idCommandeProduit;
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
    public Integer getQuantite() {
        return quantite;
    }
    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }
    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }
    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    // Getters & Setters
}
