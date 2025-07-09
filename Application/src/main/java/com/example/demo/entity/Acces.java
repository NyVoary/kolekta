package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "acces")
public class Acces {

    public static final String[] theRoles = new String[] {
            "admin", "employe"
    };

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_employe")
    private Employes employes;

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

    public void setRole(String roles) {
        for (String role : theRoles) {
            if (role.equals(roles)) {
                this.role = roles;
                return;
            }
        }
    }

    public static String[] getTheroles() {
        return theRoles;
    }

    public Employes getEmployes() {
        return employes;
    }

    public void setEmployes(Employes employes) {
        this.employes = employes;
    }

}
