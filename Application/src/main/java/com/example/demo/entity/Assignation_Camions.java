package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Assignations_camions")
public class Assignation_Camions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_assignation_camion")
    private Long id;

    @Column(name = "id_camion")
    private Long idCamion;

    @Column(name = "depart")
    private LocalDateTime depart;

    @Column(name = "arrivee")
    private LocalDateTime arrivee;

    @Column(name = "type")
    private String type;

    @ManyToOne
    @JoinColumn(name = "id_camion", insertable = false, updatable = false)
    private Camions camion;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdCamion() { return idCamion; }
    public void setIdCamion(Long idCamion) { this.idCamion = idCamion; }

    public LocalDateTime getDepart() { return depart; }
    public void setDepart(LocalDateTime depart) { this.depart = depart; }

    public LocalDateTime getArrivee() { return arrivee; }
    public void setArrivee(LocalDateTime arrivee) { this.arrivee = arrivee; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Camions getCamion() { return camion; }
    public void setCamion(Camions camion) { this.camion = camion; }
}