package com.example.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.lang.Long;
import java.math.BigDecimal;

@Entity
@Table(name = "commandes")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commande", columnDefinition = "integer")
    private Long idCommande;

    @Column(name = "id_client", nullable = false)
    private Long idClient;

    @Column(name = "date_commande", nullable = false)
    private LocalDate dateCommande;

    @Column(name = "montant_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTotal;
    
    @Column(name = "frais_livraison", nullable = false, precision = 10, scale = 2)
    private BigDecimal fraisLivraison;

    @Column(name = "adresse_livraison", nullable = false, length = 255)
    private String adresseLivraison;

    @Column(name = "ville_livraison", nullable = false, length = 100)
    private String villeLivraison;

    @Column(name = "telephone_livraison", nullable = false, length = 20)
    private String telephoneLivraison;

    @Column(name = "email_livraison", length = 255)
    private String emailLivraison;
    
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<CommandeStatus> commandeStatusList = new ArrayList<>();

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<CommandePaiement> paiements;
    
    public Commande() {
    }

    public Commande(Long idClient, LocalDate dateCommande, BigDecimal fraisLivraison, BigDecimal montantTotal, 
                   String adresseLivraison, String villeLivraison, String telephoneLivraison, 
                   String emailLivraison) {
        this.idClient = idClient;
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

    public List<CommandeStatus> getCommandeStatusList() {
        return commandeStatusList;
    }
    
    public void setCommandeStatusList(List<CommandeStatus> commandeStatusList) {
        this.commandeStatusList = commandeStatusList;
    }
    public Long getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Long idCommande) {
        this.idCommande = idCommande;
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

    public BigDecimal getMontantTotal() {
        return montantTotal;
    }
    
    public void setMontantTotal(BigDecimal montantTotal) {
        this.montantTotal = montantTotal;
    }
    
    public BigDecimal getFraisLivraison() {
        return fraisLivraison;
    }
    
    public void setFraisLivraison(BigDecimal fraisLivraison) {
        this.fraisLivraison = fraisLivraison;
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

    // toString method
    @Override
    public String toString() {
        return "Commande{" +
                "idCommande=" + idCommande +
                ", idClient=" + idClient +
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