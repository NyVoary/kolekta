package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "commandes")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commande")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client", nullable = false)
    private Client client;

    @Column(name = "date_commande", nullable = false)
    private LocalDate dateCommande;

    @Column(name = "frais_livraison", nullable = false)
    private double fraisLivraison;

    @Column(name = "montant_total", nullable = false)
    private double montantTotal;

    @Column(name = "adresse_livraison", nullable = false, length = 255)
    private String adresseLivraison;

    @Column(name = "ville_livraison", nullable = false, length = 100)
    private String villeLivraison;

    @Column(name = "telephone_livraison", nullable = false, length = 20)
    private String telephoneLivraison;

    @Column(name = "email_livraison", length = 255)
    private String emailLivraison;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommandeProduit> produits = new ArrayList<>();

    @Transient
    private String dernierStatut;

    // Constructeurs
    public Commande() {
    }

    public Commande(Client client, LocalDate dateCommande, double fraisLivraison, 
                   double montantTotal, String adresseLivraison, String villeLivraison,
                   String telephoneLivraison, String emailLivraison) {
        this.client = client;
        this.dateCommande = dateCommande;
        this.fraisLivraison = fraisLivraison;
        this.montantTotal = montantTotal;
        this.adresseLivraison = adresseLivraison;
        this.villeLivraison = villeLivraison;
        this.telephoneLivraison = telephoneLivraison;
        this.emailLivraison = emailLivraison;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public double getFraisLivraison() {
        return fraisLivraison;
    }

    public void setFraisLivraison(double fraisLivraison) {
        this.fraisLivraison = fraisLivraison;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
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

    public List<CommandeProduit> getProduits() {
        return produits;
    }

    public void setProduits(List<CommandeProduit> produits) {
        this.produits = produits;
    }

    public String getDernierStatut() {
        return dernierStatut;
    }
    
    public void setDernierStatut(String dernierStatut) {
        this.dernierStatut = dernierStatut;
    }

    // Méthodes utilitaires
    public void addProduit(CommandeProduit produit) {
        produits.add(produit);
        produit.setCommande(this);
    }

    public void removeProduit(CommandeProduit produit) {
        produits.remove(produit);
        produit.setCommande(null);
    }

    // Calcul du montant total en fonction des produits
    public void calculerMontantTotal() {
        this.montantTotal = produits.stream()
                .mapToDouble(CommandeProduit::getMontantTotal)
                .sum() + fraisLivraison;
    }
}