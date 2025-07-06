package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Commandes_Camions")
public class CommandeCamion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCommandeCamion;

    @ManyToOne
    @JoinColumn(name = "id_assignation_camion", nullable = false)
    private AssignationCamion assignationCamion;

    @ManyToOne
    @JoinColumn(name = "id_commande", nullable = false)
    private Commandes commande;

    @Column(nullable = false)
    private LocalDateTime dateAssignation;

    public Long getIdCommandeCamion() {
        return idCommandeCamion;
    }

    public void setIdCommandeCamion(Long idCommandeCamion) {
        this.idCommandeCamion = idCommandeCamion;
    }

    public AssignationCamion getAssignationCamion() {
        return assignationCamion;
    }

    public void setAssignationCamion(AssignationCamion assignationCamion) {
        this.assignationCamion = assignationCamion;
    }

    public Commandes getCommande() {
        return commande;
    }

    public void setCommande(Commandes commande) {
        this.commande = commande;
    }

    public LocalDateTime getDateAssignation() {
        return dateAssignation;
    }

    public void setDateAssignation(LocalDateTime dateAssignation) {
        this.dateAssignation = dateAssignation;
    }

    // Getters et Setters
}