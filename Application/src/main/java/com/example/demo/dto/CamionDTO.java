package com.example.demo.dto;

public class CamionDTO {

    String immatriculation;
    int capacite;
    String chauffeur_nom;
    String chauffeur_telephone;
    public String getImmatriculation() {
        return immatriculation;
    }
    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }
    public int getCapacite() {
        return capacite;
    }
    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }
    
    public String getChauffeur_nom() {
        return chauffeur_nom;
    }
    public void setChauffeur_nom(String chauffeur_nom) {
        this.chauffeur_nom = chauffeur_nom;
    }
    public String getChauffeur_telephone() {
        return chauffeur_telephone;
    }
    public void setChauffeur_telephone(String chauffeur_telephone) {
        this.chauffeur_telephone = chauffeur_telephone;
    }
}
