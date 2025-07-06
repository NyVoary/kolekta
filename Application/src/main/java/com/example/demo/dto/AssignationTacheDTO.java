package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.entity.AssignationTache.Priorite;

public class AssignationTacheDTO {

    private String titre;
    private String description;
    private Long id_employe;
    private Priorite priorite;
    private String status;
    private LocalDateTime date_debut;
    private LocalDateTime date_fin;

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

    public Long getId_employe() {
        return id_employe;
    }

    public void setId_employe(Long id_employe) {
        this.id_employe = id_employe;
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
        this.status = status;
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
