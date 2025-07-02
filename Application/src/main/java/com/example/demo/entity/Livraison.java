package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Livraisons")
public class Livraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLivraison;

    @ManyToOne
    @JoinColumn(name = "id_commande", nullable = false)
    private Commande commande;

    @Column(nullable = false)
    private LocalDate dateLivraison;

    public Long getIdLivraison() {
        return idLivraison;
    }

    public void setIdLivraison(Long idLivraison) {
        this.idLivraison = idLivraison;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public LocalDate getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(LocalDate dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

}