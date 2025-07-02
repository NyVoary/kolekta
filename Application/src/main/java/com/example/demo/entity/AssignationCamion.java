package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Assignations_camions")
public class AssignationCamion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAssignationCamion;

    @ManyToOne
    @JoinColumn(name = "id_camion", nullable = false)
    private Camion camion;

    @Column(nullable = false)
    private LocalDateTime depart;

    @Column(nullable = false)
    private LocalDateTime arrivee;

    @Column(nullable = false)
    private String type;

    public Long getIdAssignationCamion() {
        return idAssignationCamion;
    }

    public void setIdAssignationCamion(Long idAssignationCamion) {
        this.idAssignationCamion = idAssignationCamion;
    }

    public Camion getCamion() {
        return camion;
    }

    public void setCamion(Camion camion) {
        this.camion = camion;
    }

    public LocalDateTime getDepart() {
        return depart;
    }

    public void setDepart(LocalDateTime depart) {
        this.depart = depart;
    }

    public LocalDateTime getArrivee() {
        return arrivee;
    }

    public void setArrivee(LocalDateTime arrivee) {
        this.arrivee = arrivee;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Getters et Setters
}