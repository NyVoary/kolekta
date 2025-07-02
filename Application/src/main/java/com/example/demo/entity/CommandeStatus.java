package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Commande_Status")
public class CommandeStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCommandeStatus;

    @ManyToOne
    @JoinColumn(name = "id_commande", nullable = false)
    private Commande commande;

    @ManyToOne
    @JoinColumn(name = "id_status", nullable = false)
    private Status status;

    @Column(name = "date_changement", nullable = false)
    private LocalDate dateChangement;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "id_employe")
    private Employe employe;

    @Column(name = "commentaire_employe")
    private String commentaireEmploye;

    // Getters et Setters
    public Long getIdCommandeStatus() { return idCommandeStatus; }
    public void setIdCommandeStatus(Long idCommandeStatus) { this.idCommandeStatus = idCommandeStatus; }
    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public LocalDate getDateChangement() { return dateChangement; }
    public void setDateChangement(LocalDate dateChangement) { this.dateChangement = dateChangement; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    public Employe getEmploye() { return employe; }
    public void setEmploye(Employe employe) { this.employe = employe; }
    public String getCommentaireEmploye() { return commentaireEmploye; }
    public void setCommentaireEmploye(String commentaireEmploye) { this.commentaireEmploye = commentaireEmploye; }
}