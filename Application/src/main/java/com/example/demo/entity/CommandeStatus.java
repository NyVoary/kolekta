package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "commande_status")
public class CommandeStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commande_status")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_commande", nullable = false)
    private Commandes commande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_status", nullable = false)
    private Status status;

    @Column(name = "date_changement", nullable = false)
    private LocalDate dateChangement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employe")
    private Employe employe;

    @Column(name = "commentaire_employe", columnDefinition = "TEXT")
    private String commentaireEmploye;

    // Constructeurs
    public CommandeStatus() {
    }

    public CommandeStatus(Commandes commande, Status status, LocalDate dateChangement) {
        this.commande = commande;
        this.status = status;
        this.dateChangement = dateChangement;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Commandes getCommande() {
        return commande;
    }

    public void setCommande(Commandes commande) {
        this.commande = commande;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getDateChangement() {
        return dateChangement;
    }

    public void setDateChangement(LocalDate dateChangement) {
        this.dateChangement = dateChangement;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public String getCommentaireEmploye() {
        return commentaireEmploye;
    }

    public void setCommentaireEmploye(String commentaireEmploye) {
        this.commentaireEmploye = commentaireEmploye;
    }

    // Méthode utilitaire pour obtenir le libellé du statut
    public String getLibelleStatus() {
        return status != null ? status.getLibelle() : null;
    }
}
