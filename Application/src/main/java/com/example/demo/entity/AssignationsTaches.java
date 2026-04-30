package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "assignationstaches")
public class AssignationsTaches {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tache")
    private Long idTache;

    @Column(name = "titre", nullable = false, length = 20)
    private String titre;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employe", nullable = false)
    private Employe employe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_priorite", nullable = false)
    private PrioritesTaches priorite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "statusactuelle", nullable = false)
    private StatusTaches status;

    @Column(name = "date_debut", nullable = false)
    private LocalDateTime dateDebut;

    @Column(name = "date_fin", nullable = false)
    private LocalDateTime dateFin;

    // Constructeurs
    public AssignationsTaches() {
    }

    public AssignationsTaches(String titre, String description, Employe employe, 
                            PrioritesTaches priorite, StatusTaches status, 
                            LocalDateTime dateDebut, LocalDateTime dateFin) {
        this.titre = titre;
        this.description = description;
        this.employe = employe;
        this.priorite = priorite;
        this.status = status;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    // Getters et Setters
    public Long getIdTache() {
        return idTache;
    }

    public void setIdTache(Long idTache) {
        this.idTache = idTache;
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

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public PrioritesTaches getPriorite() {
        return priorite;
    }

    public void setPriorite(PrioritesTaches priorite) {
        this.priorite = priorite;
    }

    public StatusTaches getStatus() {
        return status;
    }

    public void setStatus(StatusTaches status) {
        this.status = status;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    // Méthode toString()
    @Override
    public String toString() {
        return "AssignationsTaches{" +
                "idTache=" + idTache +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", employe=" + employe +
                ", priorite=" + priorite +
                ", status=" + status +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }
}