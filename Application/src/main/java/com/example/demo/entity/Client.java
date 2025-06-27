package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clients")
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    private Long idClient;
    
    @Column(name = "nom", nullable = false, length = 100)
    private String nom;
    
    @Column(name = "prenoms", nullable = false, length = 100)
    private String prenoms;
    
    @Column(name = "dtn", nullable = false)
    private LocalDate dateNaissance;
    
    @Column(name = "sexe", length = 1)
    private String sexe;
    
    @Column(name = "email", length = 255)
    private String email;
    
    @Column(name = "telephone", nullable = false, length = 20)
    private String telephone;
    
    @Column(name = "adresse", nullable = false, length = 255)
    private String adresse;
    
    @Column(name = "mdp", length = 100)
    private String motDePasse;
    
    @Column(name = "image", length = 100)
    private String image;
    
   
    public Client() {}
    
    public Client(String nom, String prenoms, LocalDate dateNaissance, String sexe, 
                 String email, String telephone, String adresse, String motDePasse) {
        this.nom = nom;
        this.prenoms = prenoms;
        this.dateNaissance = dateNaissance;
        this.sexe = sexe;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.motDePasse = motDePasse;
    }
    
   
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
    
    public LocalDate getDateNaissance() {
        return dateNaissance;
    }
    
    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
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
    
    public String getMotDePasse() {
        return motDePasse;
    }
    
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    public String getNomComplet() {
        return prenoms + " " + nom;
    }
}