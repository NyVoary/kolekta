package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "commandes")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commande")
    private Long id;

    @Column(name = "id_client", nullable = false)
    private Long idClient;

    @Column(name = "date_commande", nullable = false)
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

    // One-to-One avec Livraison
    @OneToOne(mappedBy = "commande", cascade = CascadeType.ALL)
    private Livraison livraison;

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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

    public Livraison getLivraison() {
        return livraison;
    }

    public void setLivraison(Livraison livraison) {
        this.livraison = livraison;
    }

    public Long getIdCommande() {
        return id;
    }


}
