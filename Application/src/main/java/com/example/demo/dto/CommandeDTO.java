package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CommandeDTO {
    private Long idCommande;
    private LocalDate dateCommande;
    private BigDecimal fraisLivraison;
    private BigDecimal montantTotal;
    private String adresseLivraison;
    private String villeLivraison;
    private String telephoneLivraison;
    private String emailLivraison;
    private String clientNom;
    private String statut;

    // Getters et Setters
    public Long getIdCommande() { return idCommande; }
    public void setIdCommande(Long idCommande) { this.idCommande = idCommande; }
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
    public String getClientNom() { return clientNom; }
    public void setClientNom(String clientNom) { this.clientNom = clientNom; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}