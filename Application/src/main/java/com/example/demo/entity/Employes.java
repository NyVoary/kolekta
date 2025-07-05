package com.example.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Employes {

    public enum Mode_paiement {
        Virement,
        Chèque,
        Espèces
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_employe;

    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String prenoms;
    @Column(nullable = false)
    private LocalDate dtn;
    private char sexe;
    private String email;
    private String telephone;
    private String mdp;
    private String adresse;
    private String post;
    @Column(nullable = false)
    private LocalDate date_embauche;
    @Column(nullable = false)
    private LocalDate date_fin_embauche;
    @Column(nullable = false)
    private BigDecimal salaire;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Mode_paiement mode_paiement;
    private String image;

    public Long getId_employe() {
        return id_employe;
    }

    public void setId_employe(Long id_employe) {
        this.id_employe = id_employe;
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

    public char getSexe() {
        return sexe;
    }

    public void setSexe(char sexe) {
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

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setDate_embauche(LocalDate date_embauche) {
        this.date_embauche = date_embauche;
    }

    public void setDate_fin_embauche(LocalDate date_fin_embauche) {
        this.date_fin_embauche = date_fin_embauche;
    }

    public void setSalaire(BigDecimal salaire) {
        this.salaire = salaire;
    }

    public void setMode_paiement(Mode_paiement mode_paiement) {
        this.mode_paiement = mode_paiement;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDate getDate_embauche() {
        return date_embauche;
    }

    public LocalDate getDate_fin_embauche() {
        return date_fin_embauche;
    }

    public BigDecimal getSalaire() {
        return salaire;
    }

    public Mode_paiement getMode_paiement() {
        return mode_paiement;
    }

    public String getImage() {
        return image;
    }

}