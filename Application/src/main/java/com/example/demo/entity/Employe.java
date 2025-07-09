package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "employes")
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_employe")
    private Long id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenoms;

    @Column(nullable = false)
    private LocalDate dtn;  // date de naissance

    @Column(nullable = false, length = 1)
    private char sexe;  // 'M' ou 'F'

    @Column(length = 255)
    private String email;

    @Column(length = 20)
    private String telephone;

    @Column(length = 100)
    private String mdp;  // mot de passe (devrait être hashé en pratique)

    @Column(length = 100)
    private String adresse;

    @Column(length = 100)
    private String poste;

    @Column(name = "date_embauche", nullable = false)
    private LocalDate dateEmbauche;

    @Column(name = "date_fin_embauche", nullable = false)
    private LocalDate dateFinEmbauche;

    @Column(nullable = false)
    private double salaire;

    @Column(name = "mode_paiement", nullable = false, length = 100)
    private String modePaiement;  // 'Virement', 'Chèque', 'Espèces'

    @Column(length = 100)
    private String image;

    // Constructeurs
    public Employe() {
    }

    public Employe(String nom, String prenoms, LocalDate dtn, char sexe, 
                  String email, String telephone, String mdp, String adresse,
                  String poste, LocalDate dateEmbauche, LocalDate dateFinEmbauche,
                  double salaire, String modePaiement) {
        this.nom = nom;
        this.prenoms = prenoms;
        this.dtn = dtn;
        this.sexe = sexe;
        this.email = email;
        this.telephone = telephone;
        this.mdp = mdp;
        this.adresse = adresse;
        this.poste = poste;
        this.dateEmbauche = dateEmbauche;
        this.dateFinEmbauche = dateFinEmbauche;
        this.salaire = salaire;
        this.modePaiement = modePaiement;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (sexe != 'M' && sexe != 'F') {
            throw new IllegalArgumentException("Le sexe doit être 'M' ou 'F'");
        }
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

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public LocalDate getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(LocalDate dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    public LocalDate getDateFinEmbauche() {
        return dateFinEmbauche;
    }

    public void setDateFinEmbauche(LocalDate dateFinEmbauche) {
        this.dateFinEmbauche = dateFinEmbauche;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        if (!modePaiement.equals("Virement") && !modePaiement.equals("Chèque") && !modePaiement.equals("Espèces")) {
            throw new IllegalArgumentException("Mode de paiement invalide");
        }
        this.modePaiement = modePaiement;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // Méthodes utilitaires
    public String getNomComplet() {
        return nom + " " + prenoms;
    }

    public boolean estActif() {
        LocalDate aujourdhui = LocalDate.now();
        return !aujourdhui.isBefore(dateEmbauche) && !aujourdhui.isAfter(dateFinEmbauche);
    }
}