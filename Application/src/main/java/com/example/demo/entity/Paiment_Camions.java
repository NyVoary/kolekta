package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Paiment_Camions")
public class Paiment_Camions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paiement_camion")
    private Long id;

    @Column(name = "id_commande_camion")
    private Long idCommandeCamion;

    @Column(name = "montant")
    private double montant;

    @Column(name = "date_paiement")
    private LocalDate datePaiement;

    @Column(name = "mode_paiement")
    private String modePaiement;

    @ManyToOne
    @JoinColumn(name = "id_commande_camion", insertable = false, updatable = false)
    private Commande_Camions commandeCamion;

    // Constructeur
    public Paiment_Camions() {
        this.datePaiement = LocalDate.now();
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdCommandeCamion() { return idCommandeCamion; }
    public void setIdCommandeCamion(Long idCommandeCamion) { this.idCommandeCamion = idCommandeCamion; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public LocalDate getDatePaiement() { return datePaiement; }
    public void setDatePaiement(LocalDate datePaiement) { this.datePaiement = datePaiement; }

    public String getModePaiement() { return modePaiement; }
    public void setModePaiement(String modePaiement) { this.modePaiement = modePaiement; }

    public Commande_Camions getCommandeCamion() { return commandeCamion; }
    public void setCommandeCamion(Commande_Camions commandeCamion) { this.commandeCamion = commandeCamion; }
}