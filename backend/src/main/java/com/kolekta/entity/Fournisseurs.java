package com.kolekta.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity @Table(name = "fournisseurs")
@Getter @Setter @NoArgsConstructor
public class Fournisseurs {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fournisseur")
    private Long idFournisseur;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 255)
    private String adresse;

    @Column(length = 20)
    private String telephone;

    @Column(length = 255)
    private String email;

    @Column(length = 100)
    private String image;

    @Column(name = "date_creation")
    private LocalDate dateCreation = LocalDate.now();

    @Column(name = "estactif", nullable = false)
    private Boolean estActif = true;

    @Column(name = "date_desactivation")
    private LocalDate dateDesactivation;
}
