package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Comparator;

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
    private List<CommandeProduit> produits;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("dateChangement DESC") // Pour avoir l'historique trié par date
    private List<CommandeStatus> commandesStatus = new ArrayList<>();

    public List<CommandeStatus> getCommandesStatus() {
        return commandesStatus;
    }

    public void setCommandesStatus(List<CommandeStatus> commandesStatus) {
        this.commandesStatus = commandesStatus;
    }

    public Status getStatutActuel() {
        if (this.commandesStatus == null || this.commandesStatus.isEmpty()) {
            return null;
        }
        return this.commandesStatus.stream()
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

    public List<CommandeProduit> getProduits() {
        return produits;
    }

    public void setProduits(List<CommandeProduit> produits) {
        this.produits = produits;
    }
}