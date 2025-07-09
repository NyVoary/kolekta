package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Assignations_taches")
public class AssignationTache {

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
    
    @Column(name = "priorité", nullable = false, length = 20)
    private String priorite;
    
    @Column(name = "statusActuelle", nullable = true, length = 20)
    private String statusActuelle = "En attente";
    
    @Column(name = "date_debut", nullable = false)
    private LocalDateTime dateDebut;
    
    @Column(name = "date_fin", nullable = false)
    private LocalDateTime dateFin;
    
    @OneToMany(mappedBy = "tache", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StatusTacheHistorique> historiquesStatus = new ArrayList<>();
    
    // Constructeurs
    public AssignationTache() {
    }

    public AssignationTache(String titre, String description, Employe employe, 
                          String priorite, String statusActuelle,
                          LocalDateTime dateDebut, LocalDateTime dateFin) {
        this.titre = titre;
        this.description = description;
        this.employe = employe;
        this.priorite = priorite;
        this.statusActuelle = statusActuelle;
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

    public String getPriorite() {
        return priorite;
    }

    public void setPriorite(String priorite) {
        this.priorite = priorite;
    }

    public String getStatusActuelle() {
        return statusActuelle;
    }

    public void setStatusActuelle(String statusActuelle) {
        this.statusActuelle = statusActuelle;
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

    public List<StatusTacheHistorique> getHistoriquesStatus() {
        return historiquesStatus;
    }

    public void setHistoriquesStatus(List<StatusTacheHistorique> historiquesStatus) {
        this.historiquesStatus = historiquesStatus;
    }

    // Méthodes utilitaires
    public void addHistoriqueStatus(StatusTacheHistorique historique) {
        historiquesStatus.add(historique);
        historique.setTache(this);
    }

    public void removeHistoriqueStatus(StatusTacheHistorique historique) {
        historiquesStatus.remove(historique);
        historique.setTache(null);
    }

    // Equals et HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssignationTache that = (AssignationTache) o;
        return Objects.equals(idTache, that.idTache);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTache);
    }

    @Override
    public String toString() {
        return "AssignationTache{" +
                "idTache=" + idTache +
                ", titre='" + titre + '\'' +
                ", priorite='" + priorite + '\'' +
                ", statusActuelle='" + statusActuelle + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }

    // Enums pour les valeurs constantes
    public enum Priorite {
        BASSE("Basse"),
        MOYENNE("Moyenne"),
        HAUTE("Haute"),
        URGENTE("Urgente");

        private final String valeur;

        Priorite(String valeur) {
            this.valeur = valeur;
        }

        public String getValeur() {
            return valeur;
        }
    }

    public enum StatusTache {
        EN_ATTENTE("En attente"),
        EN_COURS("En cours"),
        TERMINEE("Terminée"),
        ANNULEE("Annulée");

        private final String valeur;

        StatusTache(String valeur) {
            this.valeur = valeur;
        }

        public String getValeur() {
            return valeur;
        }
    }
}