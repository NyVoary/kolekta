package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Disponibilites_Fournisseur")
public class Disponibilite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dispo")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_fournisseur")
    private Fournisseurs fournisseur;

    @ManyToOne
    @JoinColumn(name = "id_produit")
    private Produits produit;

    @Column(name = "quantite_disponible")
    private int quantite;

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fournisseurs getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseurs fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Produits getProduit() {
        return produit;
    }

    public void setProduit(Produits produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
