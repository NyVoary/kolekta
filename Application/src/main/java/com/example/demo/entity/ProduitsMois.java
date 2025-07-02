package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "produit_mois")
public class ProduitsMois {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produit_mois")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_produit", nullable = false)
    private Produit produit;

    @Min(1)
    @Max(12)
    @Column(nullable = false)
    private int mois;

    // Constructeurs
    public ProduitsMois() {
    }

    public ProduitsMois(Produit produit, int mois) {
        this.produit = produit;
        this.mois = mois;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Produit getProduits() {
        return produit;
    }

    public void setProduits(Produit produit) {
        this.produit = produit;
    }

    public int getMois() {
        return mois;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }

}
