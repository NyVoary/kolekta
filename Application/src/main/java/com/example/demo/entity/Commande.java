package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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

    @Column(name = "frais_livraison", nullable = false)
    private BigDecimal fraisLivraison;

    @Column(name = "montant_total", nullable = false)
    private BigDecimal montantTotal;

    @Column(name = "adresse_livraison", nullable = false)
    private String adresseLivraison;

    @Column(name = "ville_livraison", nullable = false)
    private String villeLivraison;

    @Column(name = "telephone_livraison", nullable = false)
    private String telephoneLivraison;

    @Column(name = "email_livraison")
    private String emailLivraison;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<CommandeProduit> commandeProduits;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<CommandeStatus> commandeStatuses;

    // Getters et Setters
    public Long getIdCommande() { return idCommande; }
    public void setIdCommande(Long idCommande) { this.idCommande = idCommande; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    public LocalDate getDateCommande() { return dateCommande; }
    public void setDateCommande(LocalDate dateCommande) { this.dateCommande = dateCommande; }
    public BigDecimal getFraisLivraison() { return fraisLivraison; }
    public void setFraisLivraison(BigDecimal fraisLivraison) { this.fraisLivraison = fraisLivraison; }
    public BigDecimal getMontantTotal() { return montantTotal; }
    public void setMontantTotal(BigDecimal montantTotal) { this.montantTotal = montantTotal; }
    public String getAdresseLivraison() { return adresseLivraison; }
    public void setAdresseLivraison(String adresseLivraison) { this.adresseLivraison = adresseLivraison; }
    public String getVilleLivraison() { return villeLivraison; }
    public void setVilleLivraison(String villeLivraison) { this.villeLivraison = villeLivraison; }
    public String getTelephoneLivraison() { return telephoneLivraison; }
    public void setTelephoneLivraison(String telephoneLivraison) { this.telephoneLivraison = telephoneLivraison; }
    public String getEmailLivraison() { return emailLivraison; }
    public void setEmailLivraison(String emailLivraison) { this.emailLivraison = emailLivraison; }
    public List<CommandeProduit> getCommandeProduits() { return commandeProduits; }
    public void setCommandeProduits(List<CommandeProduit> commandeProduits) { this.commandeProduits = commandeProduits; }
    public List<CommandeStatus> getCommandeStatuses() { return commandeStatuses; }
    public void setCommandeStatuses(List<CommandeStatus> commandeStatuses) { this.commandeStatuses = commandeStatuses; }
}