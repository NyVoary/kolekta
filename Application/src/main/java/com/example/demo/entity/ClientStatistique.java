package com.example.demo.entity;

public class ClientStatistique {
    private String libelle;
    private Long count;
    private Double montant;
    private Long nbCommandes;
    private Double panierMoyen;

    public ClientStatistique() {}

    // Constructeur pour les statistiques avec 3 paramètres
    public ClientStatistique(String libelle, Long count, Double montant) {
        this.libelle = libelle;
        this.count = count;
        this.montant = montant;
    }
    
    public ClientStatistique(String libelle, Long count, Double montant, Long nbCommandes, Double panierMoyen) {
        this.libelle = libelle;
        this.count = count;
        this.montant = montant;
        this.nbCommandes = nbCommandes;
        this.panierMoyen = panierMoyen;
    }

    public String getLibelle() {
        return libelle;
    }
    
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    
    public Long getCount() {
        return count;
    }
    
    public void setCount(Long count) {
        this.count = count;
    }
    
    public Double getMontant() {
        return montant;
    }
    
    public void setMontant(Double montant) {
        this.montant = montant;
    }

    // Ajout des nouveaux getters/setters
    public Long getNbCommandes() {
        return nbCommandes;
    }

    public void setNbCommandes(Long nbCommandes) {
        this.nbCommandes = nbCommandes;
    }

    public Double getPanierMoyen() {
        return panierMoyen;
    }

    public void setPanierMoyen(Double panierMoyen) {
        this.panierMoyen = panierMoyen;
    }

    // Méthode de formatage
    public String getMontantFormatted() {
        return montant != null ? String.format("%,.2f Ar", montant) : "N/A";
    }
    
    public String getPanierMoyenFormatted() {
        return panierMoyen != null ? String.format("%,.2f Ar", panierMoyen) : "N/A";
    }
}