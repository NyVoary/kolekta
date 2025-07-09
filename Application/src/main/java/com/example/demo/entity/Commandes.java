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
@Table(name = "Commandes")
public class Commandes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commande")
    private Long id;

    @Column(name = "id_client")
    private Long idClient;

    @Column(name = "date_commande")
    private LocalDate dateCommande;

    @Column(name = "frais_livraison")
    private double fraisLivraison;

    @Column(name = "montant_total")
    private double montantTotal;

    @Column(name = "adresse_livraison")
    private String adresseLivraison;

    @Column(name = "ville_livraison")
    private String villeLivraison;

    @Column(name = "telephone_livraison")
    private String telephoneLivraison;

    @Column(name = "email_livraison")
    private String emailLivraison;

    @ManyToOne
    @JoinColumn(name = "id_client", insertable = false, updatable = false)
    private Clients client;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdClient() { return idClient; }
    public void setIdClient(Long idClient) { this.idClient = idClient; }

    public LocalDate getDateCommande() { return dateCommande; }
    public void setDateCommande(LocalDate dateCommande) { this.dateCommande = dateCommande; }

    public double getFraisLivraison() { return fraisLivraison; }
    public void setFraisLivraison(double fraisLivraison) { this.fraisLivraison = fraisLivraison; }

    public double getMontantTotal() { return montantTotal; }
    public void setMontantTotal(double montantTotal) { this.montantTotal = montantTotal; }

    public String getAdresseLivraison() { return adresseLivraison; }
    public void setAdresseLivraison(String adresseLivraison) { this.adresseLivraison = adresseLivraison; }

    public String getVilleLivraison() { return villeLivraison; }
    public void setVilleLivraison(String villeLivraison) { this.villeLivraison = villeLivraison; }

    public String getTelephoneLivraison() { return telephoneLivraison; }
    public void setTelephoneLivraison(String telephoneLivraison) { this.telephoneLivraison = telephoneLivraison; }

    public String getEmailLivraison() { return emailLivraison; }
    public void setEmailLivraison(String emailLivraison) { this.emailLivraison = emailLivraison; }

    public Clients getClient() { return client; }
    public void setClient(Clients client) { this.client = client; }
}