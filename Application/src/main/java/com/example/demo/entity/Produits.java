package com.example.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "produits")
public class Produits {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produit")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_fournisseur", nullable = false)
    private Fournisseurs fournisseurs;

    @Column(nullable = false)
    private String nom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_categorie", nullable = false)
    private Categories categories;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String unite_mesure;

    @Column(nullable = false)
    private BigDecimal prix_achat;

    @Column(nullable = false)
    private BigDecimal prix_vente;

    @Column(nullable = false)
    private LocalDate date_ajout;

    private String image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fournisseurs getFournisseurs() {
        return fournisseurs;
    }

    public void setFournisseurs(Fournisseurs fournisseurs) {
        this.fournisseurs = fournisseurs;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnite_mesure() {
        return unite_mesure;
    }

    public void setUnite_mesure(String unite_mesure) {
        this.unite_mesure = unite_mesure;
    }

    public BigDecimal getPrix_achat() {
        return prix_achat;
    }

    public void setPrix_achat(BigDecimal prix_achat) {
        this.prix_achat = prix_achat;
    }

    public BigDecimal getPrix_vente() {
        return prix_vente;
    }

    public void setPrix_vente(BigDecimal prix_vente) {
        this.prix_vente = prix_vente;
    }

    public LocalDate getDate_ajout() {
        return date_ajout;
    }

    public void setDate_ajout(LocalDate date_ajout) {
        this.date_ajout = date_ajout;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
