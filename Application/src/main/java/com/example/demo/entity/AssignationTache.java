package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "assignations_taches")
public class AssignationTache {

    public enum Priorite {
        Basse, Moyenne, Haute, Urgente
    }

    public static final String[] theStatus = new String[] {
        "En attente",
         "En cours",
         "Terminée",
         "Annulée"
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_tache;

    @Column(nullable = false)
    private String titre;
    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_employe")
    private Employes employes;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, name="priorité")
    private Priorite priorite;

    @Column(nullable = false, name = "statusactuelle")
    private String status;

    @Column(nullable = false)
    private LocalDateTime date_debut;

    @Column(nullable = false)
    private LocalDateTime date_fin;

    public Long getId_tache() {
        return id_tache;
    }

    public void setId_tache(Long id_tache) {
        this.id_tache = id_tache;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Employes getEmployes() {
        return employes;
    }

    public void setEmployes(Employes employes) {
        this.employes = employes;
    }

    public Priorite getPriorite() {
        return priorite;
    }

    public void setPriorite(Priorite priorite) {
        this.priorite = priorite;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        for (int a = 0; a < theStatus.length; a++) {
            if (status.equals(theStatus[a])) {
                this.status = status;
                return;
            }
        }
        // throws new Exception("Status non valide");
    }

    public LocalDateTime getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(LocalDateTime date_debut) {
        this.date_debut = date_debut;
    }

    public LocalDateTime getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(LocalDateTime date_fin) {
        this.date_fin = date_fin;
    }

}
