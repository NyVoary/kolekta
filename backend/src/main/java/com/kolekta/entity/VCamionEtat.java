package com.kolekta.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@Immutable
@Subselect("SELECT * FROM v_camion_etat")
@Getter
public class VCamionEtat {
    @Id
    @Column(name = "id_camion")
    private Long idCamion;

    @Column
    private String immatriculation;

    @Column
    private String modele;

    @Column(name = "capacite_kg")
    private Integer capaciteKg;

    @Column(name = "etat_camion")
    private String etatCamion;

    @Column(name = "nom_chauffeur")
    private String nomChauffeur;

    @Column(name = "prenom_chauffeur")
    private String prenomChauffeur;
}
