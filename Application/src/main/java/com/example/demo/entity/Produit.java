package com.example.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "produits")
public class Produit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produit")
    private Long idProduit;
    
    @Column(name = "id_fournisseur", nullable = false)
    private Long idFournisseur;
    
    @Column(name = "nom", nullable = false, length = 100)
    private String nom;
    
    @Column(name = "id_categorie", nullable = false)
    private Long idCategorie;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "unite_mesure", nullable = false, length = 100)
    private String uniteMesure;
    
    @Column(name = "prix_achat", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixAchat;
    
    @Column(name = "prix_vente", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixVente;
    
    @Column(name = "date_ajout", nullable = false)
    private LocalDate dateAjout;
    
    @Column(name = "image", length = 100)
    private String image;
    
   
    public Produit() {}
    
    public Produit(String nom, String description, String uniteMesure, BigDecimal prixVente, String image) {
        this.nom = nom;
        this.description = description;
        this.uniteMesure = uniteMesure;
        this.prixVente = prixVente;
        this.image = image;
    }
    
    
    public Long getIdProduit() {
        return idProduit;
    }
    
    public void setIdProduit(Long idProduit) {
        this.idProduit = idProduit;
    }
    
    public Long getIdFournisseur() {
        return idFournisseur;
    }
    
    public void setIdFournisseur(Long idFournisseur) {
        this.idFournisseur = idFournisseur;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public Long getIdCategorie() {
        return idCategorie;
    }
    
    public void setIdCategorie(Long idCategorie) {
        this.idCategorie = idCategorie;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getUniteMesure() {
        return uniteMesure;
    }
    
    public void setUniteMesure(String uniteMesure) {
        this.uniteMesure = uniteMesure;
    }
    
    public BigDecimal getPrixAchat() {
        return prixAchat;
    }
    
    public void setPrixAchat(BigDecimal prixAchat) {
        this.prixAchat = prixAchat;
    }
    
    public BigDecimal getPrixVente() {
        return prixVente;
    }
    
    public void setPrixVente(BigDecimal prixVente) {
        this.prixVente = prixVente;
    }
    
    public LocalDate getDateAjout() {
        return dateAjout;
    }
    
    public void setDateAjout(LocalDate dateAjout) {
        this.dateAjout = dateAjout;
    }
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
}