package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historiquestatustaches")
public class HistoriqueStatusTaches {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tache", referencedColumnName = "id_tache", nullable = false)
    private AssignationsTaches tache;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modifie_par", referencedColumnName = "id_employe", nullable = false)
    private Employe modifiePar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_status_taches", referencedColumnName = "id_status", nullable = false)
    private StatusTaches statusTache;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    // Constructeurs
    public HistoriqueStatusTaches() {
    }

    public HistoriqueStatusTaches(AssignationsTaches tache, Employe modifiePar, StatusTaches statusTache, LocalDateTime date) {
        this.tache = tache;
        this.modifiePar = modifiePar;
        this.statusTache = statusTache;
        this.date = date;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AssignationsTaches getTache() {
        return tache;
    }

    public void setTache(AssignationsTaches tache) {
        this.tache = tache;
    }

    public Employe getModifiePar() {
        return modifiePar;
    }

    public void setModifiePar(Employe modifiePar) {
        this.modifiePar = modifiePar;
    }

    public StatusTaches getStatusTache() {
        return statusTache;
    }

    public void setStatusTache(StatusTaches statusTache) {
        this.statusTache = statusTache;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    // Equals et HashCode (optionnel mais recommandé)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistoriqueStatusTaches)) return false;
        return id != null && id.equals(((HistoriqueStatusTaches) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // ToString
    @Override
    public String toString() {
        return "HistoriqueStatusTaches{" +
                "id=" + id +
                ", tache=" + tache.getIdTache() +
                ", modifiePar=" + modifiePar.getId() +
                ", statusTache=" + statusTache.getIdStatus() +
                ", date=" + date +
                '}';
    }
}