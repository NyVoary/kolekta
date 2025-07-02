package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Camions")
public class Camion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCamion;

    @Column(nullable = false)
    private String immatriculation;

    @Column(nullable = false)
    private BigDecimal capacite;

    private String chauffeurNom;

    private String chauffeurTelephone;

    public Long getIdCamion() {
        return idCamion;
    }

    public void setIdCamion(Long idCamion) {
        this.idCamion = idCamion;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public BigDecimal getCapacite() {
        return capacite;
    }

    public void setCapacite(BigDecimal capacite) {
        this.capacite = capacite;
    }

    public String getChauffeurNom() {
        return chauffeurNom;
    }

    public void setChauffeurNom(String chauffeurNom) {
        this.chauffeurNom = chauffeurNom;
    }

    public String getChauffeurTelephone() {
        return chauffeurTelephone;
    }

    public void setChauffeurTelephone(String chauffeurTelephone) {
        this.chauffeurTelephone = chauffeurTelephone;
    }

}