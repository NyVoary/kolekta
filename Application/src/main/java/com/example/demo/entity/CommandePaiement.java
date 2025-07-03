package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
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
    private Commande commande;

    @Column(name = "date_paiement", nullable = false)
    private LocalDate datePaiement;

    @Column(name = "montant", nullable = false, precision = 10, scale = 2)
    private BigDecimal montant;

    @Column(name = "mode_paiement", nullable = false, length = 100)
    private String modePaiement;

    // Constructeurs
    public CommandePaiement() {
    }

    public CommandePaiement(LocalDate datePaiement, BigDecimal montant, String modePaiement) {
        this.datePaiement = datePaiement;
        this.montant = montant;
        this.modePaiement = modePaiement;
    }

    // Getters et Setters
    public Integer getIdPaiement() {
        return idPaiement;
    }

    public void setIdPaiement(Integer idPaiement) {
        this.idPaiement = idPaiement;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
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