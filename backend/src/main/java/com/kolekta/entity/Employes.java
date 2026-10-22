package com.kolekta.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity @Table(name = "employes")
@Getter @Setter @NoArgsConstructor
public class Employes {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_employe")
    private Long idEmploye;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenoms;

    @Column(nullable = false)
    private LocalDate dtn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sexe")
    private Sexe sexe;

    @Column(length = 255)
    private String email;

    @Column(length = 20)
    private String telephone;

    @Column(length = 255)
    private String adresse;

    @Column(length = 100)
    private String poste;

    @Column(name = "date_embauche", nullable = false)
    private LocalDate dateEmbauche;

    @Column(name = "date_fin_embauche", nullable = false)
    private LocalDate dateFinEmbauche;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mode", nullable = false)
    private ModePaiement modePaiement;

    @Column(length = 100)
    private String image;
}
