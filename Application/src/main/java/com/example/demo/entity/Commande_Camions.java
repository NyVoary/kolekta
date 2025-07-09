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
@Table(name = "Commandes_Camions")
public class Commande_Camions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commande_camion")
    private Long id;

    @Column(name = "id_assignation_camion")
    private Long idAssignationCamion;

    @Column(name = "id_commande")
    private Long idCommande;

    @Column(name = "date_assignation")
    private LocalDateTime dateAssignation;

    @ManyToOne
    @JoinColumn(name = "id_commande", insertable = false, updatable = false)
    private Commandes commande;

    @ManyToOne
    @JoinColumn(name = "id_assignation_camion", insertable = false, updatable = false)
    private Assignation_Camions assignationCamion;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdAssignationCamion() { return idAssignationCamion; }
    public void setIdAssignationCamion(Long idAssignationCamion) { this.idAssignationCamion = idAssignationCamion; }

    public Long getIdCommande() { return idCommande; }
    public void setIdCommande(Long idCommande) { this.idCommande = idCommande; }

    public LocalDateTime getDateAssignation() { return dateAssignation; }
    public void setDateAssignation(LocalDateTime dateAssignation) { this.dateAssignation = dateAssignation; }

    public Commandes getCommande() { return commande; }
    public void setCommande(Commandes commande) { this.commande = commande; }

    public Assignation_Camions getAssignationCamion() { return assignationCamion; }
    public void setAssignationCamion(Assignation_Camions assignationCamion) { this.assignationCamion = assignationCamion; }
}