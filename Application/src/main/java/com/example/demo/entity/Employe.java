package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Employes")
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmploye;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenoms;

    @Column(nullable = false)
    private LocalDate dtn;

    @Column(length = 1)
    private String sexe;

    private String email;

    private String telephone;

    private String mdp;

    private String adresse;

    private String poste;

    @Column(nullable = false)
    private LocalDate dateEmbauche;

    @Column(nullable = false)
    private LocalDate dateFinEmbauche;

    @Column(nullable = false)
    private BigDecimal salaire;

    @Column(nullable = false)
    private String modePaiement;

    private String image;

    // Getters et Setters
    public Long getIdEmploye() { return idEmploye; }
    public void setIdEmploye(Long idEmploye) { this.idEmploye = idEmploye; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenoms() { return prenoms; }
    public void setPrenoms(String prenoms) { this.prenoms = prenoms; }
    public LocalDate getDtn() { return dtn; }
    public void setDtn(LocalDate dtn) { this.dtn = dtn; }
    public String getSexe() { return sexe; }
    public void setSexe(String sexe) { this.sexe = sexe; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public String getMdp() { return mdp; }
    public void setMdp(String mdp) { this.mdp = mdp; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public String getPoste() { return poste; }
    public void setPoste(String poste) { this.poste = poste; }
    public LocalDate getDateEmbauche() { return dateEmbauche; }
    public void setDateEmbauche(LocalDate dateEmbauche) { this.dateEmbauche = dateEmbauche; }
    public LocalDate getDateFinEmbauche() { return dateFinEmbauche; }
    public void setDateFinEmbauche(LocalDate dateFinEmbauche) { this.dateFinEmbauche = dateFinEmbauche; }
    public BigDecimal getSalaire() { return salaire; }
    public void setSalaire(BigDecimal salaire) { this.salaire = salaire; }
    public String getModePaiement() { return modePaiement; }
    public void setModePaiement(String modePaiement) { this.modePaiement = modePaiement; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}