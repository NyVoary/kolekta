package com.example.demo.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "panier")
public class Panier {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_panier")
    private Long idPanier;
    
    @Column(name = "id_client", nullable = false)
    private Long idClient;
    
    @Column(name = "date_creation", nullable = false)
    private LocalDate dateCreation;
    
    @OneToMany(mappedBy = "panier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PanierProduits> panierProduits;
    
    public Panier() {}
    
    public Panier(Long idClient, LocalDate dateCreation) {
        this.idClient = idClient;
        this.dateCreation = dateCreation;
    }
   
    public Long getIdPanier() {
        return idPanier;
    }
    
    public void setIdPanier(Long idPanier) {
        this.idPanier = idPanier;
    }
    
    public Long getIdClient() {
        return idClient;
    }
    
    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }
    
    public LocalDate getDateCreation() {
        return dateCreation;
    }
    
    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }
    
    public List<PanierProduits> getPanierProduits() {
        return panierProduits;
    }
    
    public void setPanierProduits(List<PanierProduits> panierProduits) {
        this.panierProduits = panierProduits;
    }
}