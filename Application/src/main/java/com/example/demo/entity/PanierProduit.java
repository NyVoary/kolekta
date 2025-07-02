package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "panier_produits")
public class PanierProduit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_panier_produit")
    private Long idPanierProduit;
    
    @ManyToOne
    @JoinColumn(name = "id_panier", nullable = false)
    private Panier panier;
    
    @ManyToOne
    @JoinColumn(name = "id_produit", nullable = false)
    private Produit produit;
    
    @Column(name = "quantite", nullable = false)
    private Integer quantite;
    
    @Column(name = "prix_unitaire", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixUnitaire;
    
    
    public PanierProduit() {}
    
    public PanierProduit(Panier panier, Produit produit, Integer quantite, BigDecimal prixUnitaire) {
        this.panier = panier;
        this.produit = produit;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
    }
    
    
    public Long getIdPanierProduit() {
        return idPanierProduit;
    }
    
    public void setIdPanierProduit(Long idPanierProduit) {
        this.idPanierProduit = idPanierProduit;
    }
    
    public Panier getPanier() {
        return panier;
    }
    
    public void setPanier(Panier panier) {
        this.panier = panier;
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
    
    
    public BigDecimal getSousTotal() {
        return prixUnitaire.multiply(BigDecimal.valueOf(quantite));
    }
}