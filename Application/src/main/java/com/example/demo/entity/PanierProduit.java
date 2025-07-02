package com.example.demo.entity;

import jakarta.persistence.*;

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
    private Produits produit;
    
    @Column(name = "quantite", nullable = false)
    private Integer quantite;
    
    @Column(name = "prix_unitaire", nullable = false)
    private Double prixUnitaire;
    
    
    public PanierProduit() {}
    
    public PanierProduit(Panier panier, Produits produit, Integer quantite, Double prixUnitaire) {
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
    
    public Double getPrixUnitaire() {
        return prixUnitaire;
    }
    
    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
    
    
    public Double getSousTotal() {
        return prixUnitaire * quantite;
    }
}