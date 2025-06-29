package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "commandes")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commande")
    private Long id;

    @ManyToOne
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

    @OneToMany(mappedBy = "commande", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CommandeStatus> statuts;

    // Getters et setters
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

    public List<CommandeStatus> getStatuts() {
        return statuts;
    }

    public void setStatuts(List<CommandeStatus> statuts) {
        this.statuts = statuts;
    }
}
