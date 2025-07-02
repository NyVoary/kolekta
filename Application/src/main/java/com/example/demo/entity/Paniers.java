package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "panier")
public class Paniers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_panier")
    private int id;

    @Column(name = "id_client", nullable = false)
    private Long idClient;

    @Column(name = "date_creation", nullable = false)
    private LocalDate dateCreation;

    // Si votre table a des colonnes supplémentaires, ajoutez-les ici
    // Exemple si la table a une colonne quantite :
    // @Column(name = "quantite")
    // private Integer quantite;
    
    // @Column(name = "prix_unitaire")
    // private Double prixUnitaire;

    public Paniers() {
        this.dateCreation = LocalDate.now();
        // Initialisez les autres champs si nécessaire
        // this.quantite = 0;
        // this.prixUnitaire = 0.0;
    }

    // Getters / Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Long getIdClient() { return idClient; }
    public void setIdClient(Long idClient) { this.idClient = idClient; }

    public LocalDate getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDate dateCreation) { this.dateCreation = dateCreation; }
    

    // Ajoutez les getters/setters pour les colonnes supplémentaires si nécessaire
}