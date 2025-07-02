package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Commandes")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCommande;

    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false)
    private Client client;

    @Column(nullable = false)
    private LocalDate dateCommande;

    @Column(nullable = false)
    private BigDecimal fraisLivraison;

    @Column(nullable = false)
    private BigDecimal montantTotal;

    @Column(nullable = false)
    private String adresseLivraison;

    @Column(nullable = false)
    private String villeLivraison;

    @Column(nullable = false)
    private String telephoneLivraison;

    private String emailLivraison;

    public Long getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Long idCommande) {
        this.idCommande = idCommande;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDate getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    public BigDecimal getFraisLivraison() {
        return fraisLivraison;
    }

    public void setFraisLivraison(BigDecimal fraisLivraison) {
        this.fraisLivraison = fraisLivraison;
    }

    public BigDecimal getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(BigDecimal montantTotal) {
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

    // Getters et Setters
}