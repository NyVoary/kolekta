package com.example.demo.dto;

import com.example.demo.entity.CommandeProduits;
import java.time.LocalDate;
import java.util.List;

public class CommandeDTO {
    private Long idCommande;
    private LocalDate dateCommande;
    private Double fraisLivraison;
    private Double montantTotal;
    private String adresseLivraison;
    private String villeLivraison;
    private String telephoneLivraison;
    private String emailLivraison;
    private String clientNom;
    private String statut;

    // ✅ Liste des produits de la commande
    private List<CommandeProduits> commandeProduits;

    // Getters et Setters
    public Long getIdCommande() { return idCommande; }
    public void setIdCommande(Long idCommande) { this.idCommande = idCommande; }

    public LocalDate getDateCommande() { return dateCommande; }
    public void setDateCommande(LocalDate dateCommande) { this.dateCommande = dateCommande; }

    public Double getFraisLivraison() { return fraisLivraison; }
    public void setFraisLivraison(Double fraisLivraison) { this.fraisLivraison = fraisLivraison; }

    public Double getMontantTotal() { return montantTotal; }
    public void setMontantTotal(Double montantTotal) { this.montantTotal = montantTotal; }

    public String getAdresseLivraison() { return adresseLivraison; }
    public void setAdresseLivraison(String adresseLivraison) { this.adresseLivraison = adresseLivraison; }

    public String getVilleLivraison() { return villeLivraison; }
    public void setVilleLivraison(String villeLivraison) { this.villeLivraison = villeLivraison; }

    public String getTelephoneLivraison() { return telephoneLivraison; }
    public void setTelephoneLivraison(String telephoneLivraison) { this.telephoneLivraison = telephoneLivraison; }

    public String getEmailLivraison() { return emailLivraison; }
    public void setEmailLivraison(String emailLivraison) { this.emailLivraison = emailLivraison; }

    public String getClientNom() { return clientNom; }
    public void setClientNom(String clientNom) { this.clientNom = clientNom; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public List<CommandeProduits> getCommandeProduits() { return commandeProduits; }
    public void setCommandeProduits(List<CommandeProduits> commandeProduits) { this.commandeProduits = commandeProduits; }
}
