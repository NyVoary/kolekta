package com.example.demo.dto;

import java.time.LocalDate;

public class LivraisonDTO {
    private Long idLivraison;
    private LocalDate dateLivraison;
    private String clientNom;
    private String adresseLivraison;
    private String villeLivraison;
    private String immatriculation;
    private String chauffeurNom;
    private String statut;

    // Getters et Setters
    public Long getIdLivraison() { return idLivraison; }
    public void setIdLivraison(Long idLivraison) { this.idLivraison = idLivraison; }
    public LocalDate getDateLivraison() { return dateLivraison; }
    public void setDateLivraison(LocalDate dateLivraison) { this.dateLivraison = dateLivraison; }
    public String getClientNom() { return clientNom; }
    public void setClientNom(String clientNom) { this.clientNom = clientNom; }
    public String getAdresseLivraison() { return adresseLivraison; }
    public void setAdresseLivraison(String adresseLivraison) { this.adresseLivraison = adresseLivraison; }
    public String getVilleLivraison() { return villeLivraison; }
    public void setVilleLivraison(String villeLivraison) { this.villeLivraison = villeLivraison; }
    public String getImmatriculation() { return immatriculation; }
    public void setImmatriculation(String immatriculation) { this.immatriculation = immatriculation; }
    public String getChauffeurNom() { return chauffeurNom; }
    public void setChauffeurNom(String chauffeurNom) { this.chauffeurNom = chauffeurNom; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}