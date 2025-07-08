package com.example.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Comparator;
import java.time.LocalDate;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "commandes")
public class Commandes {
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
    private Double fraisLivraison;

    @Column(name = "montant_total", nullable = false)
    private Double montantTotal;

    @Column(name = "adresse_livraison", nullable = false)
    private String adresseLivraison;

    @Column(name = "ville_livraison", nullable = false)
    private String villeLivraison;

    @Column(name = "telephone_livraison", nullable = false)
    private String telephoneLivraison;

    @Column(name = "email_livraison")
    private String emailLivraison;

    @OneToMany(mappedBy = "commande", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommandeProduits> produits;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("dateChangement DESC") // Pour avoir l'historique trié par date
    private List<CommandeStatus> status = new ArrayList<>();

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<CommandePaiement> paiements;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<CommandeProduits> commandeProduits;

    @Transient
    private String dernierStatut;

    public Commandes() {
    }

    public Commandes(Client client, LocalDate dateCommande, Double fraisLivraison, Double montantTotal,
            String adresseLivraison, String villeLivraison, String telephoneLivraison,
            String emailLivraison) {
        this.client = client;
        this.dateCommande = dateCommande;
        this.fraisLivraison = fraisLivraison;
        this.montantTotal = montantTotal;
        this.adresseLivraison = adresseLivraison;
        this.villeLivraison = villeLivraison;
        this.telephoneLivraison = telephoneLivraison;
        this.emailLivraison = emailLivraison;
    }

    public List<CommandePaiement> getPaiements() {
        return paiements;
    }

    public void setPaiements(List<CommandePaiement> paiements) {
        this.paiements = paiements;
    }

    public List<CommandeStatus> getStatus() {
        return status;
    }

    public void setstatus(List<CommandeStatus> status) {
        this.status = status;
    }

    public Status getStatutActuel() {
        if (this.status == null || this.status.isEmpty()) {
            return null;
        }
        return this.status.stream()
                .filter(cs -> cs.getDateChangement() != null) // Filtre les dates null
                .max(Comparator.comparing(CommandeStatus::getDateChangement))
                .map(CommandeStatus::getStatus)
                .orElse(null);
    }

    public String formatterDateCommande() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateCommande != null ? dateCommande.format(formatter) : "";
    }

    public String formatterDateCommandeAvecJour() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", Locale.US);
        return dateCommande != null ? dateCommande.format(formatter) : "";
    }

    public int getProgression() {
        Status statut = getStatutActuel();
        if (statut == null)
            return 0;

        switch (statut.getLibelle()) {
            case "En attente de validation":
            case "Validée":
            case "En cours de traitement":
                return 25; // Preparation
            case "Expédiée":
                return 50; // Expedition
            case "Livraison en cours":
                return 75; // Livraison
            case "Livrée":
                return 100; // Terminé
            case "Annulée":
                return -1; // Spécial pour annulé
            default:
                return 0;
        }
    }

    // Getters and Setters
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

    public List<CommandeProduits> getProduits() {
        return produits;
    }

    public void setProduits(List<CommandeProduits> produits) {
        this.produits = produits;
    }

    public List<CommandeStatus> getCommandeStatusList() {
        return status;
    }

    public void setCommandeStatusList(List<CommandeStatus> commandeStatusList) {
        this.status = commandeStatusList;
    }

    public List<CommandeStatus> getStatuts() {
        return status;
    }

    public void setStatuts(List<CommandeStatus> statuts) {
        this.status = statuts;
    }

    // Méthodes utilitaires
    public void addProduit(CommandeProduits produit) {
        produits.add(produit);
        produit.setCommande(this);
    }

    public void removeProduit(CommandeProduits produit) {
        produits.remove(produit);
        produit.setCommande(null);
    }

    // Calcul du montant total en fonction des produits
    public void calculerMontantTotal() {
        this.montantTotal = produits.stream()
                .mapToDouble(CommandeProduits::getMontantTotal)
                .sum() + fraisLivraison;
    }

    public String getDernierStatut() {
        return dernierStatut;
    }

    public void setDernierStatut(String dernierStatut) {
        this.dernierStatut = dernierStatut;
    }

    public List<CommandeProduits> getCommandeProduits() {
        return commandeProduits;
    }

    public void setCommandeProduits(List<CommandeProduits> commandeProduits) {
        this.commandeProduits = commandeProduits;
    }

    // toString method
    @Override
    public String toString() {
        return "Commande{" +
                "idCommande=" + id +
                ", client=" + client +
                ", dateCommande=" + dateCommande +
                ", fraisLivraison=" + fraisLivraison +
                ", montantTotal=" + montantTotal +
                ", adresseLivraison='" + adresseLivraison + '\'' +
                ", villeLivraison='" + villeLivraison + '\'' +
                ", telephoneLivraison='" + telephoneLivraison + '\'' +
                ", emailLivraison='" + emailLivraison + '\'' +
                '}';
    }
}
