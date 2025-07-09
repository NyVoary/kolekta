package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "prioritestaches")
public class PrioritesTaches {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_priorite")
    private Long idPriorite;

    @Column(name = "libelle", nullable = false, length = 100)
    private String libelle;

    // Constructeurs
    public PrioritesTaches() {
        // Constructeur par défaut nécessaire pour JPA
    }

    public PrioritesTaches(String libelle) {
        this.libelle = libelle;
    }

    // Getters et Setters
    public Long getIdPriorite() {
        return idPriorite;
    }

    public void setIdPriorite(Long idPriorite) {
        this.idPriorite = idPriorite;
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
        return "PrioritesTaches{" +
                "idPriorite=" + idPriorite +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}