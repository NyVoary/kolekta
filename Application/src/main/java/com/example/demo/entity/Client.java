package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    private Long idClient;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenoms;

    @Column(nullable = false)
    private LocalDate dtn;

    @Column(nullable = false, length = 1)
    private String sexe;

    private String email;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String adresse;

    private String mdp;

    private String image;

    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDate dateCreation = LocalDate.now(); // Valeur par défaut

    // --- Getters et Setters ---

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    public LocalDate getDtn() {
        return dtn;
    }

    public void setDtn(LocalDate dtn) {
        this.dtn = dtn;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }
    
    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }
}
