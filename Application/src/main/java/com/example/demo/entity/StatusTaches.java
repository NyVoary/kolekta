package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "statustaches")
public class StatusTaches {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status")
    private Long idStatus;

    @Column(name = "libelle", nullable = false, length = 100)
    private String libelle;

    // Constructeurs
    public StatusTaches() {
        // Constructeur par défaut nécessaire pour JPA
    }

    public StatusTaches(String libelle) {
        this.libelle = libelle;
    }

    // Getters et Setters
    public Long getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Long idStatus) {
        this.idStatus = idStatus;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    // Méthode toString()
    @Override
    public String toString() {
        return "StatusTaches{" +
                "idStatus=" + idStatus +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}