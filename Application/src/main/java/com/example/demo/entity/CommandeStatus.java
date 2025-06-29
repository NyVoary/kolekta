package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "commande_status")
public class CommandeStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commande_status")
    private Integer idCommandeStatus;

    @Column(name = "id_commande", nullable = false)
    private Long idCommande;

    @ManyToOne
    @JoinColumn(name = "id_status", nullable = false)
    private Status status;

    @Column(name = "date_changement", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate dateChangement;

    @Column(name = "id_client")
    private Long idClient;

    @Column(name = "id_employe")
    private Long idEmploye;

    @Column(name = "commentaire_employe", columnDefinition = "TEXT")
    private String commentaireEmploye;

    // Ajouter la relation ManyToOne
    @ManyToOne
    @JoinColumn(name = "id_commande", insertable = false, updatable = false)
    private Commande commande;

    // Constructeurs, getters et setters
    public CommandeStatus() {
    }

    // Getters et Setters
    public Integer getIdCommandeStatus() {
        return idCommandeStatus;
    }

    public void setIdCommandeStatus(Integer idCommandeStatus) {
        this.idCommandeStatus = idCommandeStatus;
    }

    public Long getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Long idCommande) {
        this.idCommande = idCommande;
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

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public Long getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(Long idEmploye) {
        this.idEmploye = idEmploye;
    }

    public String getCommentaireEmploye() {
        return commentaireEmploye;
    }

    public void setCommentaireEmploye(String commentaireEmploye) {
        this.commentaireEmploye = commentaireEmploye;
    }
}