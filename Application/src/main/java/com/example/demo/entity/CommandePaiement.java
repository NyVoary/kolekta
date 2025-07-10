package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "commande_paiement")
public class CommandePaiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commande_paiement")
    private Integer idPaiement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_commande", referencedColumnName = "id_commande", nullable = false)
    private Commandes commande;

    @Column(name = "date_paiement", nullable = false)
    private LocalDate datePaiement;

    @Column(name = "montant", nullable = false)
    private Double montant;

    @Column(name = "mode_paiement", nullable = false, length = 100)
    private String modePaiement;

    // Constructeurs
    public CommandePaiement() {
    }

    public CommandePaiement(LocalDate datePaiement, Double montant, String modePaiement) {
        this.datePaiement = datePaiement;
        this.montant = montant;
        this.modePaiement = modePaiement;
    }

    // Getters et Setters
    public Integer getIdPaiement() {
        return idPaiement;
    }

    public CommandePaiement(Commandes commande, LocalDate datePaiement, Double montant, String modePaiement) {
        this.commande = commande;
        this.datePaiement = datePaiement;
        this.montant = montant;
        this.modePaiement = modePaiement;
    }

    public void setIdPaiement(Integer idPaiement) {
        this.idPaiement = idPaiement;
    }

    public Commandes getCommande() {
        return commande;
    }

    public void setCommande(Commandes commande) {
        this.commande = commande;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    @Override
    public String toString() {
        return "CommandePaiement{" +
                "idPaiement=" + idPaiement +
                ", datePaiement=" + datePaiement +
                ", montant=" + montant +
                ", modePaiement='" + modePaiement + '\'' +
                '}';
    }
}