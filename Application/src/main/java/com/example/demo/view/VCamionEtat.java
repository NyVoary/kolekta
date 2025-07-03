package com.example.demo.view;


import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "v_camion_etat")
public class VCamionEtat {
    


    @Id
    @Column(name = "id_camion")
    private int id;

    @Column(name = "id_assignation_camion")
    private int id_assignation_camion;

    @Column(name = "immatriculation")
    private String immatriculation;

    @Column(name = "capacite")
    private double capacite;

    @Column(name = "chauffeur_nom")
    private String chauffeur_nom;

    @Column(name = "chauffeur_telephone")
    private String chauffeur_telephone;

    @Column(name = "etat_camion")
    private String etatCamion;


    public String getEtatCamion() {
        return etatCamion;
    }

    public void setEtatCamion(String etatCamion) {
        this.etatCamion = etatCamion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_assignation_camion() {
        return id_assignation_camion;
    }

    public void setId_assignation_camion(int id_assignation_camion) {
        this.id = id_assignation_camion;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public double getCapacite() {
        return capacite;
    }

    public void setCapacite(double capacite) {
        this.capacite = capacite;
    }
    public void setCapacite(String capacite) throws Exception {
        try{
            double capaciteNombre = Double.parseDouble(capacite);
            setCapacite(capaciteNombre); 
        }catch(Exception e){
            throw new Exception("Le capacite n'est pas un nombre");
        }
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