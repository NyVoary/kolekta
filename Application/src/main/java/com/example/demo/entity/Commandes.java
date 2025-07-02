package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "commandes")
public class Commandes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commande")
    private Long id;

    @Column(name = "id_client", nullable = false)
    private Long idClient;

    @Column(name = "date_commande", nullable = false)
    private LocalDate dateCommande;

    @Column(name = "frais_livraison")
    private Double fraisLivraison;

    @Column(name = "montant_total")
    private Double montantTotal;

    @Column(name = "adresse_livraison")
    private String adresseLivraison;

    @Column(name = "ville_livraison")
    private String villeLivraison;

    @Column(name = "telephone_livraison")
    private String telephoneLivraison;

    @Column(name = "email_livraison")
    private String emailLivraison;

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public LocalDate getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Double getFraisLivraison() {
        return fraisLivraison;
    }

    public void setFraisLivraison(Double fraisLivraison) {
        this.fraisLivraison = fraisLivraison;
    }

    public Double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(Double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public String getAdresseLivraison() {
        return adresseLivraison;
    }

    public void setAdresseLivraison(String adresseLivraison) {
        this.adresseLivraison = adresseLivraison;
    }

    public String getVilleLivraison() {
        return villeLivraison;
    }

    public void setVilleLivraison(String villeLivraison) {
        this.villeLivraison = villeLivraison;
    }

    public String getTelephoneLivraison() {
        return telephoneLivraison;
    }

    public void setTelephoneLivraison(String telephoneLivraison) {
        this.telephoneLivraison = telephoneLivraison;
    }

    public String getEmailLivraison() {
        return emailLivraison;
    }

    public void setEmailLivraison(String emailLivraison) {
        this.emailLivraison = emailLivraison;
    }
}
