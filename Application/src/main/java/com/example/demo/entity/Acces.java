package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "acces")
public class Acces {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_acces")
    private Long idAcces;

    @Column(name = "nom_utilisateur", nullable = false, unique = true)
    private String nomUtilisateur;

    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "id_employe")
    private Long idEmploye;

    // Getters & Setters
    public Long getIdAcces() {
        return idAcces;
    }

    public void setIdAcces(Long idAcces) {
        this.idAcces = idAcces;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(Long idEmploye) {
        this.idEmploye = idEmploye;
    }
}
