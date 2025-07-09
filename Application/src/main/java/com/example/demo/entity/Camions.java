package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Camions")
public class Camions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_camion")
    private Long id;

    @Column(name = "immatriculation")
    private String immatriculation;

    @Column(name = "capacite")
    private double capacite;

    @Column(name = "chauffeur_nom")
    private String chauffeurNom;

    @Column(name = "chauffeur_telephone")
    private String chauffeurTelephone;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getImmatriculation() { return immatriculation; }
    public void setImmatriculation(String immatriculation) { this.immatriculation = immatriculation; }

    public double getCapacite() { return capacite; }
    public void setCapacite(double capacite) { this.capacite = capacite; }

    public String getChauffeurNom() { return chauffeurNom; }
    public void setChauffeurNom(String chauffeurNom) { this.chauffeurNom = chauffeurNom; }

    public String getChauffeurTelephone() { return chauffeurTelephone; }
    public void setChauffeurTelephone(String chauffeurTelephone) { this.chauffeurTelephone = chauffeurTelephone; }
}