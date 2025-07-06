package com.example.demo.entity;

import java.time.LocalDateTime;


import jakarta.persistence.*;

@Entity
@Table(name = "assignations_camions")
public class AssignationCamion {
    
    public enum TypeAssignation {
        Collecte,
        Livraison
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_assignation_camion")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_camion", nullable = false)
    private Camion camion;

    @Column(name = "depart", nullable = false)
    private LocalDateTime depart;

    @Column(name = "arrivee")
    private LocalDateTime arrivee;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TypeAssignation type;

    // Getters / Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public TypeAssignation getType() {
        return type;
    }

    public void setType(TypeAssignation type) {
        this.type = type;
    }
}
