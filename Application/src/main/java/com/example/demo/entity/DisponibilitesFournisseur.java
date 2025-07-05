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

@Entity
@Table(name = "disponibilites_fournisseur")
public class DisponibilitesFournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dispo")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_fournisseur")
    private Fournisseurs fournisseur;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_produit")
    private Produits produit;

    @Column(name = "quantite_disponible")
    private int quantiteDisponible;

    public DisponibilitesFournisseur() {
    }

    public DisponibilitesFournisseur(Fournisseurs fournisseur, Produits produit, int quantiteDisponible) {
        this.fournisseur = fournisseur;
        this.produit = produit;
        this.quantiteDisponible = quantiteDisponible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getQuantiteDisponible() {
        return quantiteDisponible;
    }

    public void setQuantiteDisponible(int quantiteDisponible) {
        this.quantiteDisponible = quantiteDisponible;
    }
}