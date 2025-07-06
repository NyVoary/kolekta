package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Employes.Mode_paiement;
import com.example.demo.entity.Employes.Sexe;

public class EmployesDTO {

    private Long id_employe;
    private String nom;
    private String prenoms;
    private LocalDate dtn;
    private Sexe sexe;
    private String email;
    private String telephone;
    private String mdp;
    private String adresse;
    private String post;
    private LocalDate date_embauche;
    private LocalDate date_fin_embauche;
    private BigDecimal salaire;
    private Mode_paiement mode_paiement;

    private MultipartFile image;

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

    public LocalDate getDate_embauche() {
        return date_embauche;
    }

    public void setDate_embauche(LocalDate date_embauche) {
        this.date_embauche = date_embauche;
    }

    public LocalDate getDate_fin_embauche() {
        return date_fin_embauche;
    }

    public void setDate_fin_embauche(LocalDate date_fin_embauche) {
        this.date_fin_embauche = date_fin_embauche;
    }

    public BigDecimal getSalaire() {
        return salaire;
    }

    public void setSalaire(BigDecimal salaire) {
        this.salaire = salaire;
    }

    public Mode_paiement getMode_paiement() {
        return mode_paiement;
    }

    public void setMode_paiement(Mode_paiement mode_paiement) {
        this.mode_paiement = mode_paiement;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

}
