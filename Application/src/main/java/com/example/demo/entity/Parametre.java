package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Parametre")
public class Parametre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_parametre;

    private String libelle;
    private Integer valeur;

    // Getters & Setters
    public Integer getId_parametre() { return id_parametre; }
    public void setId_parametre(Integer id) { this.id_parametre = id; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public Integer getValeur() { return valeur; }
    public void setValeur(Integer valeur) { this.valeur = valeur; }
}
