package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "Status_Taches")
public class StatusTacheHistorique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tache", nullable = false)
    private AssignationTache tache;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modifie_par", nullable = false)
    private Employe modifiePar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employe", nullable = false)
    private Employe employe;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    // Constructeurs
    public StatusTacheHistorique() {
    }

    public StatusTacheHistorique(AssignationTache tache, Employe modifiePar, Employe employe, 
                               String status, LocalDateTime date) {
        this.tache = tache;
        this.modifiePar = modifiePar;
        this.employe = employe;
        this.status = status;
        this.date = date;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AssignationTache getTache() {
        return tache;
    }

    public void setTache(AssignationTache tache) {
        this.tache = tache;
    }

    public Employe getModifiePar() {
        return modifiePar;
    }

    public void setModifiePar(Employe modifiePar) {
        this.modifiePar = modifiePar;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    // Equals et HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusTacheHistorique that = (StatusTacheHistorique) o;
        return Objects.equals(id, that.id) && 
               Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date);
    }

    // toString
    @Override
    public String toString() {
        return "StatusTacheHistorique{" +
                "id=" + id +
                ", tache=" + (tache != null ? tache.getIdTache() : null) +
                ", modifiePar=" + (modifiePar != null ? modifiePar.getId() : null) +
                ", employe=" + (employe != null ? employe.getId() : null) +
                ", status=" + status +
                ", date=" + date +
                '}';
    }

    // Méthode utilitaire pour créer un nouvel historique
    public static StatusTacheHistorique createNew(AssignationTache tache, Employe currentUser, 
                                                 String newStatus) {
        return new StatusTacheHistorique(
                tache,
                currentUser,
                tache.getEmploye(),
                newStatus,
                LocalDateTime.now()
        );
    }
}