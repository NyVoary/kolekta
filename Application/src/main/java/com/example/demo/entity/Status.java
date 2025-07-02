package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStatus;

    @Column(nullable = false)
    private String libelle;

    // Getters et Setters
    public Long getIdStatus() { return idStatus; }
    public void setIdStatus(Long idStatus) { this.idStatus = idStatus; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
}