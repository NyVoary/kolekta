package com.example.demo.entity;
<<<<<<< HEAD
import jakarta.persistence.*;
import java.time.LocalDate;


=======

import jakarta.persistence.*;
import java.time.LocalDate;

>>>>>>> g1-details-commande
@Entity
@Table(name = "commande_status")
public class CommandeStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commande_status")
<<<<<<< HEAD
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_commande", nullable = false)
    private Commandes commande;
=======
    private Integer idCommandeStatus;

    @Column(name = "id_commande", nullable = false)
    private Long idCommande;
>>>>>>> g1-details-commande

    @ManyToOne
    @JoinColumn(name = "id_status", nullable = false)
    private Status status;

    @Column(name = "date_changement", nullable = false)
<<<<<<< HEAD
    private LocalDate dateChangement;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

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
=======
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
>>>>>>> g1-details-commande
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

<<<<<<< HEAD
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

=======
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
>>>>>>> g1-details-commande
}